package com.dongchyeon.simplechatapp_v2.data.repository

import com.dongchyeon.simplechatapp_v2.data.datasource.AuthDataSource
import com.dongchyeon.simplechatapp_v2.data.model.request.LoginReq
import com.dongchyeon.simplechatapp_v2.data.model.response.LoginRes
import com.dongchyeon.simplechatapp_v2.data.model.response.SignupRes
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authDataSource: AuthDataSource
) {

    suspend fun login(loginReq: LoginReq): Result<LoginRes> =
        authDataSource.login(loginReq)

    suspend fun signup(
        data: HashMap<String, RequestBody>,
        profile_img: MultipartBody.Part
    ): Result<SignupRes> =
        authDataSource.signup(data, profile_img)

}