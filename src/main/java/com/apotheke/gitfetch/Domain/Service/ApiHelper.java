package com.apotheke.gitfetch.Domain.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;

@Component
public class ApiHelper {

    @Value("${github.baseUrl}")
    public String baseUrl;

    public static final String SEARCH_RESOURCE = "/search/repositories";
    public static final String SORT_BY = "sort";
    public static final String ORDER = "order";
    public static final String STARS = "stars";
    public static final String DESC = "desc";
    public static final String PER_PAGE = "per_page";
    public static final String Q = "q";
    public static final String CREATED_DATE_QUERY = "created:>";
    public static final String LANGUAGE_QUERY = "language:";

    public URI buildUri(boolean mostPopular, Integer top, LocalDate created, String programmingLanguage) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUrl + SEARCH_RESOURCE);

        if (mostPopular) {
            addSortByStarsQueryParameter(builder);
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

    public void addSortByStarsQueryParameter(UriComponentsBuilder builder) {
        builder.queryParam(SORT_BY, STARS)
                .queryParam(ORDER, DESC);
    }
}
