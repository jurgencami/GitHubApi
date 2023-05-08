package com.apotheke.gitfetch.View.Mapper;

import com.apotheke.gitfetch.Domain.Model.Repositories;
import com.apotheke.gitfetch.Domain.Model.Repository;
import com.apotheke.gitfetch.View.Model.GitHubRepositories;
import com.apotheke.gitfetch.View.Model.GitHubRepository;
import org.springframework.stereotype.Component;

@Component
public class GitHubRepositoryMapper {

    public GitHubRepositories toGitHubRepositories(Repositories repositories) {
        GitHubRepositories gitHubRepositories = new GitHubRepositories();
        gitHubRepositories.setGitHubRepositories(repositories.getItems().stream().map(this::toGitHubRepository).toList());
        return gitHubRepositories;
    }

    private GitHubRepository toGitHubRepository(Repository repository) {
        GitHubRepository gitHubRepository = new GitHubRepository();
        gitHubRepository.setName(repository.getName());
        gitHubRepository.setUrl(repository.getUrl());
        if (repository.getOwner() != null) {
            gitHubRepository.setOwnerUrl(repository.getOwner().getUrl());
        }
        return gitHubRepository;
    }
}
