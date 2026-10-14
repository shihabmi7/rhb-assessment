package com.shihab.rhbaccess.external;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.shihab.rhbaccess.common.ExternalCallException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

class ExternalApiServiceTest {

    MockRestServiceServer server;
    ExternalApiService service;

    @BeforeEach
    void setUp() {
        RestClient.Builder builder = RestClient.builder().baseUrl("https://www.google.com");
        server = MockRestServiceServer.bindTo(builder).build();
        service = new ExternalApiService(builder.build());
    }

    @Test
    void returnsStatusAndLength() {
        server.expect(requestTo("https://www.google.com/")).andRespond(withSuccess("<html></html>", null));

        ExternalApiResult result = service.checkGoogle();

        assertThat(result.status()).isEqualTo(200);
        assertThat(result.contentLength()).isEqualTo(13);
    }

    @Test
    void wrapsRemoteFailure() {
        server.expect(requestTo("https://www.google.com/")).andRespond(withServerError());

        assertThatThrownBy(() -> service.checkGoogle()).isInstanceOf(ExternalCallException.class);
    }
}
