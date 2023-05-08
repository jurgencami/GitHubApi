package com.apotheke.gitfetch.View.Controller;

import com.apotheke.gitfetch.Domain.Service.ApiHelper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;

import static com.apotheke.gitfetch.Domain.Service.ApiHelper.*;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"management.port=0"})
@ExtendWith(SpringExtension.class)
@TestPropertySource("/application.properties")
public class BaseIT {
    protected WireMockServer wireMockServer;
    @LocalServerPort
    protected int port;
    protected final ApiHelper apiHelper = new ApiHelper();
    protected final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        wireMockServer = new WireMockServer(options().port(1080).notifier(new ConsoleNotifier(true)));
        wireMockServer.start();
    }

    @AfterEach
    public void teardown() {
        wireMockServer.resetAll();
        wireMockServer.stop();
    }

    protected void githubRepositoryStub(URI uri, HttpStatus httpStatus, JsonNode responseBody) {
        wireMockServer.stubFor(get(uri.toString())
                .withHeader(HttpHeaders.ACCEPT, equalTo(MediaType.APPLICATION_JSON_VALUE))
                .willReturn(aResponse()
                        .withStatus(httpStatus.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withJsonBody(responseBody)
                )
        );
    }

    protected URI buildUri(boolean mostPopular, Integer top, LocalDate created, String programmingLanguage) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(SEARCH_RESOURCE);

        if (mostPopular) {
            apiHelper.addSortByStarsQueryParameter(builder);
        }

        if (top != null) {
            builder.queryParam(PER_PAGE, top);
        }

        if (created != null) {
            builder.queryParam(Q, CREATED_DATE_QUERY + created);
        }

        if (programmingLanguage != null) {
            builder.queryParam(Q, LANGUAGE_QUERY + programmingLanguage);
        }

        return builder.build().encode().toUri();
    }
}
