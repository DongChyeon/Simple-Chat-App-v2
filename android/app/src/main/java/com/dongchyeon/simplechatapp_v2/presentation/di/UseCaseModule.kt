package com.dongchyeon.simplechatapp_v2.presentation.di

import com.dongchyeon.simplechatapp_v2.data.repository.AuthRepository
import com.dongchyeon.simplechatapp_v2.data.repository.UserRepository
import com.dongchyeon.simplechatapp_v2.domain.GetOnlineUsersUseCase
import com.dongchyeon.simplechatapp_v2.domain.GetProfileUseCase
import com.dongchyeon.simplechatapp_v2.domain.LoginUseCase
import com.dongchyeon.simplechatapp_v2.domain.SignupUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun providesLoginUseCase(authRepository: AuthRepository): LoginUseCase {
        return LoginUseCase(authRepository)
    }

    @Singleton
    @Provides
    fun providesSignupUseCase(authRepository: AuthRepository): SignupUseCase {
        return SignupUseCase(authRepository)
    }

    @Singleton
    @Provides
    fun providesGetOnlineUsersCaseUse(userRepository: UserRepository): GetOnlineUsersUseCase {
        return GetOnlineUsersUseCase(userRepository)
    }

    @Singleton
    @Provides
    fun providesGetProfileCaseUse(userRepository: UserRepository): GetProfileUseCase {
        return GetProfileUseCase(userRepository)
    }

}