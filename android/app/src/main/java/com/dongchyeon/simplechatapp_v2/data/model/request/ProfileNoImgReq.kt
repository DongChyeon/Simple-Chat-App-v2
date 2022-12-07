package com.dongchyeon.simplechatapp_v2.data.model.request

import com.google.gson.annotations.SerializedName

data class ProfileNoImgReq(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("intro_msg")
    val introMsg: String
)
