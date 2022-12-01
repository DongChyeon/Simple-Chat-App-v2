package com.dongchyeon.simplechatapp_v2.data.datasource

import com.dongchyeon.simplechatapp_v2.data.model.response.UserRes

interface UserDataSource {

    suspend fun getOnlineUsers(): Result<UserRes>

}