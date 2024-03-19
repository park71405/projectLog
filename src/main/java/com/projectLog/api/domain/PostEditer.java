package com.projectLog.api.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostEditer {

    private final String title;
    private final String content;

    @Builder
    public PostEditer(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
