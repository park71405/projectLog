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

    public static PostEditerBuilder builder() {
        return new PostEditerBuilder();
    }

    public static class PostEditerBuilder {
        private String title;
        private String content;

        PostEditerBuilder() {
        }

        public PostEditerBuilder title(final String title) {
            if(title != null){
                this.title = title;
            }
            return this;
        }

        public PostEditerBuilder content(final String content) {
            if(content != null){
                this.content = content;
            }
            return this;
        }

        public PostEditer build() {
            return new PostEditer(this.title, this.content);
        }

        public String toString() {
            return "PostEditer.PostEditerBuilder(title=" + this.title + ", content=" + this.content + ")";
        }
    }
}
