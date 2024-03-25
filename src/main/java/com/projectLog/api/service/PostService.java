package com.projectLog.api.service;

import com.projectLog.api.domain.Post;
import com.projectLog.api.domain.PostEditer;
import com.projectLog.api.exception.PostNotFound;
import com.projectLog.api.repository.PostRepository;
import com.projectLog.api.request.PostCreate;
import com.projectLog.api.request.PostEdit;
import com.projectLog.api.request.PostSearch;
import com.projectLog.api.response.PostResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;


    public void write(PostCreate postCreate){
        //repository.save(postCreate)
        // postCreate -> Entity

        Post post = Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .build();

        postRepository.save(post);
    }

    public PostResponse get(Long id){
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        // 서비스 정책에 맞는 응답 클래스를 생성
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }

    public List<PostResponse> getList(PostSearch postSearch) {

        return postRepository.getList(postSearch).stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void edit(Long id, PostEdit postEdit){

        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        PostEditer.PostEditerBuilder postEditerBuilder = post.toEditor();

        PostEditer postEditer = postEditerBuilder
                        .title(postEdit.getTitle())
                        .content(postEdit.getContent())
                        .build();

        post.edit(postEditer);

    }

    public void delete(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        postRepository.delete(post);
    }
}
