package com.projectLog.api.response;

/*
* {
*   "code" : "400",
*   "message" : "잘못된 요청입니다.",
*   "validation": {
*       "title" : "값을 입력해주세요"
*   }
* }
* */

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ErrorResponse {

    private final String code;
    private final String message;
    private final Map<String, String> validation;

    @Builder
    public ErrorResponse(String code, String message, Map<String, String> validation) {
        this.code = code;
        this.message = message;
        this.validation = validation;
    }

    public void addValidation(String fielName, String errorMessage){
        this.validation.put(fielName, errorMessage);
    }
}
