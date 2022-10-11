package com.dongchyeon.simplechatapp_v2.retrofit.dto.request

import com.google.gson.annotations.SerializedName

data class LoginReqDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("password")
    val password: String
)
