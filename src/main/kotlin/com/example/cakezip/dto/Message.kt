package com.example.cakezip.dto

import lombok.NoArgsConstructor

@NoArgsConstructor
class Message(message: String, href: String) {
    var message = ""
    var href = ""

    init {
        this.message = message
        this.href = href
    }
}
