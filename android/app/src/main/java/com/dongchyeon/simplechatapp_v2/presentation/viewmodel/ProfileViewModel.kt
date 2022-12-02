package com.dongchyeon.simplechatapp_v2.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dongchyeon.simplechatapp_v2.SimpleChatApp.Companion.UID
import com.dongchyeon.simplechatapp_v2.data.model.User
import com.dongchyeon.simplechatapp_v2.domain.GetProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase
) : ViewModel() {

    private lateinit var user: User
    private var _profile = MutableLiveData<User>()
    val profile: LiveData<User> = _profile

    init {
        getProfile(UID)
    }

    fun getProfile(uid: String) = viewModelScope.launch {
        getProfileUseCase(uid).onSuccess {
            user = it.users[0]
            _profile.postValue(user)
            Log.d("getProfile", it.toString())
        }.onFailure {
            Log.d("error", it.toString())
        }
    }

}