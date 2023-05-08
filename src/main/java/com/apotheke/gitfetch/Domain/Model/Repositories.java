package com.apotheke.gitfetch.Domain.Model;

import java.util.List;

public class Repositories {
    private int totalCount;
    private List<Repository> items;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<Repository> getItems() {
        return items;
    }

    public void setItems(List<Repository> items) {
        this.items = items;
    }
}
