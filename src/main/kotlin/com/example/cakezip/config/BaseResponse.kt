package com.example.cakezip.config

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import lombok.AllArgsConstructor
import lombok.Getter
import com.example.cakezip.config.BaseResponseStatus.SUCCESS

@Getter
@AllArgsConstructor
@JsonPropertyOrder("isSuccess", "code", "message", "result")
class BaseResponse<T> {
    //BaseResponse 객체를 사용할때 성공, 실패 경우
    @JsonProperty("isSuccess")
    private val isSuccess: Boolean
    private val message: String
    private val code: Int

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private var result: T? = null

    // 요청에 성공한 경우
    constructor(result: T) {
        isSuccess = SUCCESS.isSuccess
        message = SUCCESS.message
        this.code = SUCCESS.code
        this.result = result
    }

    // 요청에 실패한 경우
    constructor(status: BaseResponseStatus) {
        isSuccess = status.isSuccess
        message = status.message
        this.code = status.code
    }
}
