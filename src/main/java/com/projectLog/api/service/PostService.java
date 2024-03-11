package com.projectLog.api.service;

import com.projectLog.api.domain.Post;
import com.projectLog.api.repository.PostRepository;
import com.projectLog.api.request.PostCreate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;


    public void write(PostCreate postCreate){
        //repository.save(postCreate)
        // postCreate -> Entity

        Post post = new Post(postCreate.getTitle(), postCreate.getContent());

        postRepository.save(post);
    }

}
