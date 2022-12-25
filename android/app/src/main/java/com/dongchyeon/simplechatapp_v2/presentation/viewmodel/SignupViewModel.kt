package com.dongchyeon.simplechatapp_v2.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dongchyeon.simplechatapp_v2.domain.SignupUseCase
import com.dongchyeon.simplechatapp_v2.presentation.base.BaseViewModel
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
) : BaseViewModel() {
    private val _isSignedUp: MutableLiveData<Boolean> = MutableLiveData()
    val isSignedUp: LiveData<Boolean> = _isSignedUp

    fun signup(
        id: String,
        password: String,
        name: String,
        introMsg: String,
        profileImgPath: String
    ) = viewModelScope.launch {
        if (id == "") {
            _toastMessage.value = "ID는 필수 입력 항목입니다"
            return@launch
        } else if (password == "") {
            _toastMessage.value = "비밀번호는 필수 입력 항목입니다"
            return@launch
        } else if (name == "") {
            _toastMessage.value = "이름은 필수 입력 항목입니다"
            return@launch
        } else if (introMsg == "") {
            _toastMessage.value = "소개 메시지는 필수 입력 항목입니다"
            return@launch
        } else if (profileImgPath == "") {
            _toastMessage.value = "프로필 사진을 설정하세요"
            return@launch
        }

        val image = File(profileImgPath)
        val requestFile = image.asRequestBody("image/*".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("profile_img", image.name, requestFile)

        val data = HashMap<String, RequestBody>()
        data["id"] = id.toRequestBody("text/plain".toMediaTypeOrNull())
        data["password"] = password.toRequestBody("text/plain".toMediaTypeOrNull())
        data["name"] = name.toRequestBody("text/plain".toMediaTypeOrNull())
        data["intro_msg"] = introMsg.toRequestBody("text/plain".toMediaTypeOrNull())

        signupUseCase(data, body).onSuccess {
            _isSignedUp.value = true
            _toastMessage.value = "회원가입에 성공했습니다"
            Log.d("signup", it.toString())
        }.onFailure {
            _isSignedUp.value = false
            _toastMessage.value = "회원가입에 실패했습니다"
            Log.d("error", it.toString())
        }
    }

}