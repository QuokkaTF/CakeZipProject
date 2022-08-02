package com.example.cakezip.config

import lombok.AllArgsConstructor
import lombok.Getter
import lombok.Setter

@Getter
@Setter
@AllArgsConstructor
class BaseException : Exception() {
    private val status //BaseResoinseStatus 객체에 매핑
            : BaseResponseStatus? = null
}