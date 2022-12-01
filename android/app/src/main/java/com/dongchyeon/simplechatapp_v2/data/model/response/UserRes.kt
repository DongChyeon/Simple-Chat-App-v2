package com.dongchyeon.simplechatapp_v2.data.model.response

import com.dongchyeon.simplechatapp_v2.data.model.User
import com.google.gson.annotations.SerializedName

data class UserRes(
    @SerializedName("users")
    val users: List<User>,
    @SerializedName("message")
    val message: String
)
