package com.dongchyeon.simplechatapp_v2.presentation.di

import com.dongchyeon.simplechatapp_v2.BuildConfig
import com.dongchyeon.simplechatapp_v2.SimpleChatApp.Companion.TOKEN
import com.dongchyeon.simplechatapp_v2.data.api.AuthService
import com.dongchyeon.simplechatapp_v2.data.api.UserService
import com.dongchyeon.simplechatapp_v2.data.datasource.AuthDataSource
import com.dongchyeon.simplechatapp_v2.data.datasource.AuthDataSourceImpl
import com.dongchyeon.simplechatapp_v2.data.datasource.UserDataSource
import com.dongchyeon.simplechatapp_v2.data.datasource.UserDataSourceImpl
import com.dongchyeon.simplechatapp_v2.data.repository.AuthRepository
import com.dongchyeon.simplechatapp_v2.data.repository.AuthRepositoryImpl
import com.dongchyeon.simplechatapp_v2.data.repository.UserRepository
import com.dongchyeon.simplechatapp_v2.data.repository.UserRepositoryImpl
import com.dongchyeon.simplechatapp_v2.util.exception.ResultCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun providesOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        val headerInterceptor = Interceptor {
            val request = it.request()
                .newBuilder()
                .addHeader("token", TOKEN)
                .build()
            return@Interceptor it.proceed(request)
        }
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(headerInterceptor)
            .build()
    } else {
        OkHttpClient.Builder().build()
    }

    @Singleton
    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(ResultCallAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun providesAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Singleton
    @Provides
    fun provideUserService(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Singleton
    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Singleton
    @Provides
    fun providesAuthDataSource(
        authService: AuthService,
        dispatcher: CoroutineDispatcher
    ): AuthDataSource {
        return AuthDataSourceImpl(authService, dispatcher)
    }

    @Singleton
    @Provides
    fun providesUserDataSource(
        userService: UserService,
        dispatcher: CoroutineDispatcher
    ): UserDataSource {
        return UserDataSourceImpl(userService, dispatcher)
    }

    @Singleton
    @Provides
    fun providesAuthRepository(authDataSource: AuthDataSource): AuthRepository {
        return AuthRepositoryImpl(authDataSource)
    }

    @Singleton
    @Provides
    fun providesUserRepository(userDataSource: UserDataSource): UserRepository {
        return UserRepositoryImpl(userDataSource)
    }
}