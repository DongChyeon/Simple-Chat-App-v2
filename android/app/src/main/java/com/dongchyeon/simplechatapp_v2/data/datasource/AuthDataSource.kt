package com.dongchyeon.simplechatapp_v2.data.datasource

import com.dongchyeon.simplechatapp_v2.data.api.AuthService
import com.dongchyeon.simplechatapp_v2.data.model.request.LoginReq
import com.dongchyeon.simplechatapp_v2.data.model.response.LoginRes
import com.dongchyeon.simplechatapp_v2.data.model.response.SignupRes
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class AuthDataSource @Inject constructor(
    private val authService: AuthService,
    private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun login(loginReq: LoginReq): Result<LoginRes> =
        withContext(ioDispatcher) {
            return@withContext authService.login(loginReq)
        }

    suspend fun signup(
        data: HashMap<String, RequestBody>,
        profile_img: MultipartBody.Part
    ): Result<SignupRes> =
        withContext(ioDispatcher) {
            return@withContext authService.signup(data, profile_img)
        }
}