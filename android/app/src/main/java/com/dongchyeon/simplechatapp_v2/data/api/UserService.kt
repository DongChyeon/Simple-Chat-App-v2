package com.dongchyeon.simplechatapp_v2.data.api

import com.dongchyeon.simplechatapp_v2.data.model.response.UserRes
import retrofit2.http.GET

interface UserService {
    @GET("/users/online")
    suspend fun getOnlineUsers(): Result<UserRes>
}