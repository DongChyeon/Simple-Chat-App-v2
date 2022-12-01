package com.dongchyeon.simplechatapp_v2.domain

import com.dongchyeon.simplechatapp_v2.data.model.response.UserRes
import com.dongchyeon.simplechatapp_v2.data.repository.UserRepository
import javax.inject.Inject

class GetOnlineUsersUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend operator fun invoke(): Result<UserRes> =
        userRepository.getOnlineUsers()

}