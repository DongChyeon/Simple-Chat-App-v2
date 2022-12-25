package com.dongchyeon.simplechatapp_v2.domain

import com.dongchyeon.simplechatapp_v2.data.model.response.UserRes
import com.dongchyeon.simplechatapp_v2.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchOnlineUsersUseCase @Inject constructor(private val userRepository: UserRepository) {

    operator fun invoke(): Flow<Result<UserRes>> =
        userRepository.fetchOnlineUsers()

}