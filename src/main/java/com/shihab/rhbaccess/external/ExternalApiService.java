package com.shihab.rhbaccess.external;

import com.shihab.rhbaccess.common.ExternalCallException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Service
public class ExternalApiService {

    private final RestClient restClient;

    public ExternalApiService(RestClient externalRestClient) {
        this.restClient = externalRestClient;
    }

    public ExternalApiResult checkGoogle() {
        try {
            var response = restClient.get().uri("/").retrieve().toEntity(String.class);
            String body = response.getBody() == null ? "" : response.getBody();
            return new ExternalApiResult("https://www.google.com", response.getStatusCode().value(), body.length());
        } catch (RestClientException ex) {
            throw new ExternalCallException("Unable to reach external API: " + ex.getMessage(), ex);
        }
    }
}
