package com.apotheke.gitfetch.View.Controller;

import com.apotheke.gitfetch.Domain.Model.Repositories;
import com.apotheke.gitfetch.Domain.Model.Repository;
import com.apotheke.gitfetch.Stub.RepositoryFactory;
import com.apotheke.gitfetch.View.Model.GitHubRepositories;
import com.apotheke.gitfetch.View.Model.GitHubRepository;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;


class GitHubControllerIT extends BaseIT {
    @Test
    void getRepositories() {
        int numberOfResponseItems = 10;
        Repositories repositoriesStub = RepositoryFactory.createRepositories(numberOfResponseItems);
        Repository expectedRepository = repositoriesStub.getItems().get(5);
        URI uri = buildUri(false, null, LocalDate.now(), null);
        githubRepositoryStub(uri, HttpStatus.OK, mapper.valueToTree(repositoriesStub));
        GitHubRepositories gitHubRepositories = given()
                .port(port)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/repositories?created=" + LocalDate.now())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(GitHubRepositories.class);
        List<GitHubRepository> repositories = gitHubRepositories.getGitHubRepositories();
        assertEquals(numberOfResponseItems, repositories.size());
        assertEquals(expectedRepository.getName(), repositories.get(5).getName());
        assertEquals(expectedRepository.getUrl(), repositories.get(5).getUrl());
        assertEquals(expectedRepository.getOwner().getUrl(), repositories.get(5).getOwnerUrl());
    }

    @Test
    void getRepositoriesBadRequest() {
        URI uri = buildUri(false, null, LocalDate.now(), null);
        githubRepositoryStub(uri, HttpStatus.BAD_REQUEST, null);
        given()
                .port(port)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/repositories")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}