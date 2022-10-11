package com.dongchyeon.simplechatapp_v2.retrofit.dto.response

import com.google.gson.annotations.SerializedName

data class LoginResDto(
    @SerializedName("token")
    val token: String,
    @SerializedName("message")
    val message: String
)
