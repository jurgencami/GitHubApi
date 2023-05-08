package com.apotheke.gitfetch.Stub;

import com.apotheke.gitfetch.Domain.Model.Owner;
import com.apotheke.gitfetch.Domain.Model.Repositories;
import com.apotheke.gitfetch.Domain.Model.Repository;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class RepositoryFactory {
    public static Repositories createRepositories(int count) {
        Repositories repositories = new Repositories();
        repositories.setItems(createRepositoryList(count));
        return repositories;
    }

    private static List<Repository> createRepositoryList(int count) {
        Random r = new Random();
        List<Repository> repositories = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            Repository repository = new Repository();
            repository.setId(r.nextInt());
            repository.setFullName(RandomStringUtils.randomAlphanumeric(10));
            repository.setName(RandomStringUtils.randomAlphanumeric(10));
            repository.setUrl(RandomStringUtils.randomAlphanumeric(10));
            repository.setNodeId(RandomStringUtils.randomAlphanumeric(10));
            repository.setOwner(createOwner());
            repositories.add(repository);
        }
        return repositories;
    }

    private static Owner createOwner() {
        Owner owner = new Owner();
        owner.setUrl(RandomStringUtils.randomAlphanumeric(10));
        return owner;
    }
}
