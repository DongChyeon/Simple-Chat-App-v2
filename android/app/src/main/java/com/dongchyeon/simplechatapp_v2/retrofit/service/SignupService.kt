package com.dongchyeon.simplechatapp_v2.retrofit.service

import com.dongchyeon.simplechatapp_v2.retrofit.dto.response.SignupResDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

interface SignupService {
    @Multipart
    @POST("/user/signup")
    fun signup(
        @PartMap data: HashMap<String, RequestBody>,
        @Part profile_img: MultipartBody.Part
    ): Call<SignupResDto>
}