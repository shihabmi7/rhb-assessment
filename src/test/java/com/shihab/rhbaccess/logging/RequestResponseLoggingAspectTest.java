package com.shihab.rhbaccess.logging;

import static org.assertj.core.api.Assertions.assertThat;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class RequestResponseLoggingAspectTest {

    @Autowired
    MockMvc mvc;

    @Test
    void logsRequestAndResponse() throws Exception {
        Logger logger = (Logger) LoggerFactory.getLogger(RequestResponseLoggingAspect.class);
        ListAppender<ILoggingEvent> appender = new ListAppender<>();
        appender.start();
        logger.addAppender(appender);

        mvc.perform(MockMvcRequestBuilders.get("/api/customers/1"));

        assertThat(appender.list).extracting(ILoggingEvent::getFormattedMessage)
                .anyMatch(m -> m.startsWith("REQUEST  GET /api/customers/1"))
                .anyMatch(m -> m.startsWith("RESPONSE GET /api/customers/1"));
        logger.detachAppender(appender);
    }
}
