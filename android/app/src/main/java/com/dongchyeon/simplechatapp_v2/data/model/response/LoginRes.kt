package com.dongchyeon.simplechatapp_v2.data.model.response

import com.google.gson.annotations.SerializedName

data class LoginRes(
    @SerializedName("token")
    val token: String,
    @SerializedName("message")
    val message: String
)
