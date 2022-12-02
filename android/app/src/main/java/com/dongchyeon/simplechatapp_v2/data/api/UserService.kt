package com.dongchyeon.simplechatapp_v2.data.api

import com.dongchyeon.simplechatapp_v2.data.model.response.UserRes
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {
    @GET("/users/online")
    suspend fun getOnlineUsers(): Result<UserRes>

    @GET("/user/{uid}")
    suspend fun getProfile(@Path("uid") uid: String): Result<UserRes>
}