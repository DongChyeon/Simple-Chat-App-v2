package com.dongchyeon.simplechatapp_v2.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dongchyeon.simplechatapp_v2.SimpleChatApp.Companion.TOKEN
import com.dongchyeon.simplechatapp_v2.SimpleChatApp.Companion.UID
import com.dongchyeon.simplechatapp_v2.data.model.request.LoginReq
import com.dongchyeon.simplechatapp_v2.domain.LoginUseCase
import com.dongchyeon.simplechatapp_v2.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : BaseViewModel() {
    private val _isLoggedIn: MutableLiveData<Boolean> = MutableLiveData()
    val isLoggedIn: LiveData<Boolean> = _isLoggedIn

    fun login(id: String, pw: String) = viewModelScope.launch {
        loginUseCase(LoginReq(id, pw)).onSuccess {
            TOKEN = it.token
            UID = id
            _isLoggedIn.value = true
            _toastMessage.value = "로그인에 성공했습니다"
            Log.d("login", it.toString())
        }.onFailure {
            _isLoggedIn.value = false
            _toastMessage.value = "아이디와 비밀번호를 다시 확인해주세요"
            Log.d("error", it.toString())
        }
    }

}