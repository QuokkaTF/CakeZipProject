package com.example.cakezip.dto

import org.hibernate.internal.util.type.PrimitiveWrapperHelper.ShortDescriptor

data class UserDto (
    val name: String,
    val email: String,
    val phoneNum: String,
) {}