package com.projectLog.api.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String content;

    @Builder
    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public PostEditer.PostEditerBuilder toEditor(){
        return PostEditer.builder()
                .title(title)
                .content(content);
    }

    public void edit(PostEditer postEditer) {
        title = postEditer.getTitle();
        content = postEditer.getContent();
    }
}
