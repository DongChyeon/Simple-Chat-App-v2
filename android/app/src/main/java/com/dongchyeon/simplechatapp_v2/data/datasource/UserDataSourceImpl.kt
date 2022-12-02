package com.dongchyeon.simplechatapp_v2.data.datasource

import com.dongchyeon.simplechatapp_v2.data.api.UserService
import com.dongchyeon.simplechatapp_v2.data.model.response.UserRes
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val userService: UserService,
    private val ioDispatcher: CoroutineDispatcher
) : UserDataSource {

    override suspend fun getOnlineUsers(): Result<UserRes> =
        withContext(ioDispatcher) {
            return@withContext userService.getOnlineUsers()
        }

    override suspend fun getProfile(uid: String): Result<UserRes> =
        withContext(ioDispatcher) {
            return@withContext userService.getProfile(uid)
        }

}