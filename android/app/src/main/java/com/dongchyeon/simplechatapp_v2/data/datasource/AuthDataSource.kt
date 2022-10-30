package com.dongchyeon.simplechatapp_v2.data.datasource

import com.dongchyeon.simplechatapp_v2.data.model.request.LoginReq
import com.dongchyeon.simplechatapp_v2.data.model.response.LoginRes
import com.dongchyeon.simplechatapp_v2.data.model.response.SignupRes
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface AuthDataSource {

    suspend fun login(loginReq: LoginReq): Result<LoginRes>

    suspend fun signup(
        data: HashMap<String, RequestBody>,
        profile_img: MultipartBody.Part
    ): Result<SignupRes>

}