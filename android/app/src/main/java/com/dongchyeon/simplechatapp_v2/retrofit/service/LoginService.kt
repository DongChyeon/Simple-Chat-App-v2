package com.dongchyeon.simplechatapp_v2.retrofit.service

import com.dongchyeon.simplechatapp_v2.retrofit.dto.request.LoginReqDto
import com.dongchyeon.simplechatapp_v2.retrofit.dto.response.LoginResDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("/user/login")
    fun login(@Body loginReqDto : LoginReqDto) : Call<LoginResDto>
}
