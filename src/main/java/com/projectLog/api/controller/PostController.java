package com.projectLog.api.controller;

import com.projectLog.api.domain.Post;
import com.projectLog.api.request.PostCreate;
import com.projectLog.api.service.PostService;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void post(@RequestBody @Valid PostCreate request) throws Exception { //MediaType.APPLICATION_JSON

        // Case 1. 저장한 데이터 Entity -> response로 응답하기
        // Case 2. 저장한 데이터 primary id -> response로 응답하기
        //          client에서는 수신한 id를 글 조회 api를 통해 데이터를 수신받음
        // Case 3. 응답 필요없음 -> 클라이언트에서 모든 POST(글) 데이터 context를 관리
        postService.write(request);

    }


}
