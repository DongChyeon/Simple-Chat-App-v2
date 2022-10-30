package com.dongchyeon.simplechatapp_v2.data.model.request

import com.google.gson.annotations.SerializedName

data class LoginReq(
    @SerializedName("id")
    val id: String,
    @SerializedName("password")
    val password: String
)
