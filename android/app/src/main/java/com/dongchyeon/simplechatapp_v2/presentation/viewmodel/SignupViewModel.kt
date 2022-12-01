package com.dongchyeon.simplechatapp_v2.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dongchyeon.simplechatapp_v2.domain.SignupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val signupUseCase: SignupUseCase
) : ViewModel() {

    fun signup(
        id: String,
        password: String,
        name: String,
        profileImgPath: String
    ) = viewModelScope.launch {
        val image = File(profileImgPath)
        val requestFile = image.asRequestBody("image/*".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("profile_img", image.name, requestFile)

        val data = HashMap<String, RequestBody>()
        data["id"] = id.toRequestBody("text/plain".toMediaTypeOrNull())
        data["password"] = password.toRequestBody("text/plain".toMediaTypeOrNull())
        data["name"] = name.toRequestBody("text/plain".toMediaTypeOrNull())

        signupUseCase(data, body).onSuccess {
            Log.d("signup", it.toString())
        }.onFailure {
            Log.d("error", it.toString())
        }
    }

}