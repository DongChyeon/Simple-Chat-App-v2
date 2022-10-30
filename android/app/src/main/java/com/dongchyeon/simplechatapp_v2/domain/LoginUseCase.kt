package com.dongchyeon.simplechatapp_v2.domain

import com.dongchyeon.simplechatapp_v2.data.model.request.LoginReq
import com.dongchyeon.simplechatapp_v2.data.model.response.LoginRes
import com.dongchyeon.simplechatapp_v2.data.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val authRepository: AuthRepository) {

    suspend operator fun invoke(loginReq: LoginReq): Result<LoginRes> =
        authRepository.login(loginReq)

}