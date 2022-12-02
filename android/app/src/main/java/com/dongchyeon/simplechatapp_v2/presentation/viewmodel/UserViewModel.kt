package com.dongchyeon.simplechatapp_v2.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dongchyeon.simplechatapp_v2.data.model.User
import com.dongchyeon.simplechatapp_v2.domain.GetOnlineUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val getOnlineUsersUseCase: GetOnlineUsersUseCase
) : ViewModel() {

    private lateinit var userList: List<User>
    private var _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    init {
        getOnlineUsers()
    }

    fun getOnlineUsers() = viewModelScope.launch {
        getOnlineUsersUseCase().onSuccess {
            userList = it.users
            _users.postValue(userList)
            Log.d("getOnlineUsers", it.toString())
        }.onFailure {
            Log.d("error", it.toString())
        }
    }

}