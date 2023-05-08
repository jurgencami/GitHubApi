package com.apotheke.gitfetch.View.Controller;

import com.apotheke.gitfetch.Domain.Model.Repositories;
import com.apotheke.gitfetch.Domain.Service.GitHubService;
import com.apotheke.gitfetch.View.Mapper.GitHubRepositoryMapper;
import com.apotheke.gitfetch.View.Model.GitHubRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/repositories")
public class GitHubController {

    private final GitHubService gitHubService;
    private final GitHubRepositoryMapper mapper;

    @Autowired
    public GitHubController(GitHubService gitHubService, GitHubRepositoryMapper mapper) {
        this.gitHubService = gitHubService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<GitHubRepositories> getRepositories(@RequestParam(required = false, defaultValue = "false") boolean mostPopular,
                                                              @RequestParam(required = false) Integer top,
                                                              @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate created,
                                                              @RequestParam(required = false) String language) {
        Repositories repositories = gitHubService.getRepositories(mostPopular, top, created, language);
        return ResponseEntity.ok(mapper.toGitHubRepositories(repositories));
    }
}
