package com.dongchyeon.simplechatapp_v2.data.api

import com.dongchyeon.simplechatapp_v2.data.model.request.ProfileNoImgReq
import com.dongchyeon.simplechatapp_v2.data.model.response.UserRes
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface UserService {
    @GET("/users/online")
    suspend fun getOnlineUsers(): Result<UserRes>

    @GET("/user/{uid}")
    suspend fun getProfile(@Path("uid") uid: String): Result<UserRes>

    @PUT("/user")
    suspend fun updateProfile(@Body profileNoImgReq: ProfileNoImgReq): Result<UserRes>

    @Multipart
    @PUT("/user/img")
    suspend fun updateProfileWithImg(
        @PartMap data: HashMap<String, RequestBody>,
        @Part profile_img: MultipartBody.Part
    ): Result<UserRes>
}