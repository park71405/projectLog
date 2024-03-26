package com.projectLog.api.controller;

import com.projectLog.api.exception.InvalidRequest;
import com.projectLog.api.request.PostCreate;
import com.projectLog.api.request.PostEdit;
import com.projectLog.api.request.PostSearch;
import com.projectLog.api.response.PostResponse;
import com.projectLog.api.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    // SSR -> jsp, thymeleaf
    //  -> html 렌더링
    // SPA -> vue, nuxt, react, next
    //  -> javascript + <-> API(json)

    // Ctrl + shift + T => 테스트 자동 생성

    // Http Method
    // GET, POST, PUT, PATCH, DELETE, OPTIONS, HEAD, TRACE, CONNECT
    // 글 등록 : POST Method

    //데이터를 검증하는 이유
    // 1. client 개발자가 실수로 값을 누락
    // 2. client 버그로 인한 값 누락
    // 3. 외부 해커가 값을 임의로 조작해 전달
    // 4. DB에 값 저장 시 의도치 않은 오류 발생
    // 5. 서버 개발자의 편의

    private final PostService postService;

    @PostMapping("/posts")
    public void post(@RequestBody @Valid PostCreate request) throws Exception, InvalidRequest { //MediaType.APPLICATION_JSON
        request.validate();
        postService.write(request);

    }

    /*
        /posts -> 글 전체 조회(검색 + 페이징)
        /posts/{postId} -> 글 한개만 조회
     */

    @GetMapping("/posts/{postId}")
    public PostResponse get(@PathVariable Long postId){

        return postService.get(postId);
    }

    @GetMapping("/posts")
    public List<PostResponse> getList(@ModelAttribute PostSearch postSearch){

        return postService.getList(postSearch);
    }

    @PatchMapping("/posts/{postId}")
    public void edit(@PathVariable Long postId, @RequestBody @Valid PostEdit request){
        postService.edit(postId, request);
    }

    @DeleteMapping("/posts/{postId}")
    public void delete(@PathVariable Long postId){
        postService.delete(postId);
    }

}
