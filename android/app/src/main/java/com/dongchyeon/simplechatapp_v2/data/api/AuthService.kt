package com.dongchyeon.simplechatapp_v2.data.api

import com.dongchyeon.simplechatapp_v2.data.model.request.LoginReq
import com.dongchyeon.simplechatapp_v2.data.model.response.LoginRes
import com.dongchyeon.simplechatapp_v2.data.model.response.SignupRes
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface AuthService {
    @POST("/user/login")
    suspend fun login(@Body loginReq: LoginReq): Result<LoginRes>

    @Multipart
    @POST("/user/signup")
    suspend fun signup(
        @PartMap data: HashMap<String, RequestBody>,
        @Part profile_img: MultipartBody.Part
    ): Result<SignupRes>
}
