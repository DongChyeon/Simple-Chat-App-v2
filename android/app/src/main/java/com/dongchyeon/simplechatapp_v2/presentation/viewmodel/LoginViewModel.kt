package com.dongchyeon.simplechatapp_v2.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dongchyeon.simplechatapp_v2.SimpleChatApp.Companion.token
import com.dongchyeon.simplechatapp_v2.data.model.request.LoginReq
import com.dongchyeon.simplechatapp_v2.domain.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    val isLoggedIn: MutableLiveData<Boolean> = MutableLiveData()

    fun login(id: String, pw: String) = viewModelScope.launch {
        loginUseCase(LoginReq(id, pw)).onSuccess {
            token = it.token
            isLoggedIn.postValue(true)
            Log.d("login", it.toString())
        }.onFailure {
            isLoggedIn.postValue(false)
            Log.d("error", it.toString())
        }
    }

}