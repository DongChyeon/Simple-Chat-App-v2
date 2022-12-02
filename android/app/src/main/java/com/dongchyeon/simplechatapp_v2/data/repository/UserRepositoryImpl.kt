package com.dongchyeon.simplechatapp_v2.data.repository

import com.dongchyeon.simplechatapp_v2.data.datasource.UserDataSource
import com.dongchyeon.simplechatapp_v2.data.model.response.UserRes
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource
) : UserRepository {

    override suspend fun getOnlineUsers(): Result<UserRes> =
        userDataSource.getOnlineUsers()

    override suspend fun getProfile(uid: String): Result<UserRes> =
        userDataSource.getProfile(uid)

}