package com.dongchyeon.simplechatapp_v2.data.repository

import com.dongchyeon.simplechatapp_v2.data.model.response.UserRes

interface UserRepository {

    suspend fun getOnlineUsers(): Result<UserRes>

}