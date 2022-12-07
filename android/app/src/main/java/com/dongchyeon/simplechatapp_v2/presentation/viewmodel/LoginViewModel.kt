package com.dongchyeon.simplechatapp_v2.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dongchyeon.simplechatapp_v2.SimpleChatApp.Companion.TOKEN
import com.dongchyeon.simplechatapp_v2.SimpleChatApp.Companion.UID
import com.dongchyeon.simplechatapp_v2.data.model.request.LoginReq
import com.dongchyeon.simplechatapp_v2.domain.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _isLoggedIn: MutableLiveData<Boolean> = MutableLiveData()
    val isLoggedIn: LiveData<Boolean> = _isLoggedIn

    fun login(id: String, pw: String) = viewModelScope.launch {
        loginUseCase(LoginReq(id, pw)).onSuccess {
            TOKEN = it.token
            UID = id
            _isLoggedIn.postValue(true)
            Log.d("login", it.toString())
        }.onFailure {
            _isLoggedIn.postValue(false)
            Log.d("error", it.toString())
        }
    }

}