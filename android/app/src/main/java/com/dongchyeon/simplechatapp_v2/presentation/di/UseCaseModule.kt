package com.dongchyeon.simplechatapp_v2.presentation.di

import com.dongchyeon.simplechatapp_v2.data.repository.AuthRepository
import com.dongchyeon.simplechatapp_v2.data.repository.UserRepository
import com.dongchyeon.simplechatapp_v2.domain.*
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
    fun providesLoginUseCase(authRepository: AuthRepository): LoginUseCase =
        LoginUseCase(authRepository)

    @Singleton
    @Provides
    fun providesSignupUseCase(authRepository: AuthRepository): SignupUseCase =
        SignupUseCase(authRepository)

    @Singleton
    @Provides
    fun providesFetchOnlineUsersUseCase(userRepository: UserRepository): FetchOnlineUsersUseCase =
        FetchOnlineUsersUseCase(userRepository)

    @Singleton
    @Provides
    fun providesGetProfileUseCase(userRepository: UserRepository): GetProfileUseCase =
        GetProfileUseCase(userRepository)

    @Singleton
    @Provides
    fun providesUpdateProfileUseCase(userRepository: UserRepository): UpdateProfileUseCase =
        UpdateProfileUseCase(userRepository)

}