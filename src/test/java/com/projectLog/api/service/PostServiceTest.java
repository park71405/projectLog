package com.projectLog.api.service;

import com.projectLog.api.domain.Post;
import com.projectLog.api.exception.PostNotFound;
import com.projectLog.api.repository.PostRepository;
import com.projectLog.api.request.PostCreate;
import com.projectLog.api.request.PostEdit;
import com.projectLog.api.request.PostSearch;
import com.projectLog.api.response.PostResponse;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.data.domain.Sort.Direction.DESC;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean(){
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성")
    void test1(){
        // given
        PostCreate postCreate = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        // when
        postService.write(postCreate);

        // then
        assertEquals(1L, postRepository.count());
        Post post = postRepository.findAll().get(0);
        assertEquals("제목입니다.", post.getTitle());
        assertEquals("내용입니다.", post.getContent());
    }

    @Test
    @DisplayName("글 1개 조회")
    void test2(){
        // given
        Post requsetPost = Post.builder()
                .title("foo")
                .content("bar")
                .build();

        postRepository.save(requsetPost);

        // when
        PostResponse response = postService.get(requsetPost.getId());

        // then
        assertNotNull(response);
        assertEquals(1L, postRepository.count());
        assertEquals("foo", response.getTitle());
        assertEquals("bar", response.getContent());

    }

    @Test
    @DisplayName("글 1페이지 조회")
    void test3(){
        // given
        List<Post> requestPosts = IntStream.range(0, 20)
                        .mapToObj(i -> Post.builder()
                                .title("호돌맨 제목 " + i)
                                .content("반포자이 " + i)
                                .build())
                        .collect(Collectors.toList());

        postRepository.saveAll(requestPosts);

        PostSearch postSearch = PostSearch.builder()
                .page(1)
                .size(10)
                .build();

        // when
        List<PostResponse> posts = postService.getList(postSearch);

        // then
        assertEquals(10L, posts.size());
        assertEquals("호돌맨 제목 19", posts.get(0).getTitle());

    }

    @Test
    @DisplayName("글 제목 수정")
    void test4(){
        // given
        Post post = Post.builder()
                .title("호돌맨")
                .content("반포자이")
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("호돌걸")
                .content("반포자이")
                .build();

        // when
        postService.edit(post.getId(), postEdit);

        // then
        Post changedPost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id=" + post.getId()));

        assertEquals("호돌걸", changedPost.getTitle());

    }

    @Test
    @DisplayName("글 내용 수정")
    void test5(){
        // given
        Post post = Post.builder()
                .title("호돌맨")
                .content("반포자이")
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title(null)
                .content("초가집")
                .build();

        // when
        postService.edit(post.getId(), postEdit);

        // then
        Post changedPost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id=" + post.getId()));

        assertEquals("호돌맨", changedPost.getTitle());
        assertEquals("초가집", changedPost.getContent());

    }

    @Test
    @DisplayName("게시글 삭제")
    void test6(){

        // given
        Post post = Post.builder()
                .title("호돌맨")
                .content("반포자이")
                .build();

        postRepository.save(post);

        //when
        postService.delete(post.getId());

        //then
        Assertions.assertEquals(0, postRepository.count());

    }

    @Test
    @DisplayName("글 1개 조회 - 존재하지 않는 글")
    void test7(){
        // given
        Post post = Post.builder()
                .title("호돌맨")
                .content("반포자이")
                .build();

        postRepository.save(post);

        // expected
        PostNotFound e = assertThrows(PostNotFound.class, ()->{
            postService.get(post.getId() + 1L);
        });

    }

    @Test
    @DisplayName("게시글 삭제 - 존재하지 않는 글")
    void test8(){

        // given
        Post post = Post.builder()
                .title("호돌맨")
                .content("반포자이")
                .build();

        postRepository.save(post);

        // expected
        PostNotFound e = assertThrows(PostNotFound.class, ()->{
            postService.delete(post.getId() + 1L);
        });

    }

    @Test
    @DisplayName("글 내용 수정 - 존재하지 않는 글")
    void test9(){
        // given
        Post post = Post.builder()
                .title("호돌맨")
                .content("반포자이")
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title(null)
                .content("초가집")
                .build();

        // expected
        PostNotFound e = assertThrows(PostNotFound.class, ()->{
            postService.edit(post.getId() + 1L, postEdit);
        });
    }

}