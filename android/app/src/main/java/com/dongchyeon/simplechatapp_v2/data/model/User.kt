package com.dongchyeon.simplechatapp_v2.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("uid")
    val uid: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("intro_msg")
    val introMsg: String,
    @SerializedName("profile_img")
    val profileImg: String
)
