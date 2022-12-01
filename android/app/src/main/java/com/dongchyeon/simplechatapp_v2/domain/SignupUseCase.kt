package com.dongchyeon.simplechatapp_v2.domain

import com.dongchyeon.simplechatapp_v2.data.model.response.SignupRes
import com.dongchyeon.simplechatapp_v2.data.repository.AuthRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class SignupUseCase @Inject constructor(private val authRepository: AuthRepository) {

    suspend operator fun invoke(
        data: HashMap<String, RequestBody>,
        profile_img: MultipartBody.Part
    ): Result<SignupRes> =
        authRepository.signup(data, profile_img)

}