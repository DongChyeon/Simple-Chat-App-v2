package com.dongchyeon.simplechatapp_v2.data.datasource

import com.dongchyeon.simplechatapp_v2.data.api.UserService
import com.dongchyeon.simplechatapp_v2.data.model.request.ProfileNoImgReq
import com.dongchyeon.simplechatapp_v2.data.model.response.UserRes
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class UserDataSource @Inject constructor(
    private val userService: UserService,
    private val ioDispatcher: CoroutineDispatcher
) {
    private val updateDelay = 10000L

    fun fetchOnlineUsers(): Flow<Result<UserRes>> = flow {
        while (true) {
            val users = userService.getOnlineUsers()
            emit(users)
            delay(updateDelay)
        }
    }

    suspend fun getProfile(uid: String): Result<UserRes> =
        withContext(ioDispatcher) {
            return@withContext userService.getProfile(uid)
        }

    suspend fun updateProfile(profileNoImgReq: ProfileNoImgReq): Result<UserRes> =
        withContext(ioDispatcher) {
            return@withContext userService.updateProfile(profileNoImgReq)
        }

    suspend fun updateProfileWithImg(
        data: HashMap<String, RequestBody>,
        profile_img: MultipartBody.Part
    ): Result<UserRes> =
        withContext(ioDispatcher) {
            return@withContext userService.updateProfileWithImg(data, profile_img)
        }

}