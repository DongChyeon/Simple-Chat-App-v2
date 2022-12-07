package com.dongchyeon.simplechatapp_v2.data.repository

import com.dongchyeon.simplechatapp_v2.data.datasource.UserDataSource
import com.dongchyeon.simplechatapp_v2.data.model.request.ProfileNoImgReq
import com.dongchyeon.simplechatapp_v2.data.model.response.UserRes
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDataSource: UserDataSource
) {

    fun fetchOnlineUsers(): Flow<Result<UserRes>> =
        userDataSource.fetchOnlineUsers()

    suspend fun getProfile(uid: String): Result<UserRes> =
        userDataSource.getProfile(uid)

    suspend fun updateProfile(profileNoImgReq: ProfileNoImgReq): Result<UserRes> =
        userDataSource.updateProfile(profileNoImgReq)

    suspend fun updateProfileWithImg(
        data: HashMap<String, RequestBody>,
        profile_img: MultipartBody.Part
    ): Result<UserRes> =
        userDataSource.updateProfileWithImg(data, profile_img)

}