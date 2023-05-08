package com.apotheke.gitfetch.Domain.Service;

import com.apotheke.gitfetch.Domain.Model.Repositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.LocalDate;

@Service
public class GitHubService {

    private final ApiHelper apiHelper;

    @Autowired
    public GitHubService(ApiHelper apiHelper) {
        this.apiHelper = apiHelper;
    }

    public Repositories getRepositories(boolean mostPopular, Integer top, LocalDate created, String language) {
        URI uri = apiHelper.buildUri(mostPopular, top, created, language);
        return WebClient.create(apiHelper.baseUrl)
                .mutate()
                .codecs(configure -> configure
                        .defaultCodecs()
                        .maxInMemorySize(16 * 1024 * 1024))
                .build()
                .get()
                .uri(uri)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response ->
                        Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST))
                )
                .onStatus(HttpStatus::is5xxServerError, response ->
                        Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR))
                )
                .bodyToMono(Repositories.class)
                .block();
    }
}
