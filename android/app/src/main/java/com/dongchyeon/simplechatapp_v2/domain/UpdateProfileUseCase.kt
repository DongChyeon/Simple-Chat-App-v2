package com.dongchyeon.simplechatapp_v2.domain

import com.dongchyeon.simplechatapp_v2.data.model.request.ProfileNoImgReq
import com.dongchyeon.simplechatapp_v2.data.model.response.UserRes
import com.dongchyeon.simplechatapp_v2.data.repository.UserRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class UpdateProfileUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend fun updateProfileWithImg(
        data: HashMap<String, RequestBody>,
        profile_img: MultipartBody.Part
    ): Result<UserRes> =
        userRepository.updateProfileWithImg(data, profile_img)

    suspend fun updateProfile(profileNoImgReq: ProfileNoImgReq): Result<UserRes> =
        userRepository.updateProfile(profileNoImgReq)

}