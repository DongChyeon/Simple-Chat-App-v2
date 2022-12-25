package com.dongchyeon.simplechatapp_v2.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dongchyeon.simplechatapp_v2.data.model.User
import com.dongchyeon.simplechatapp_v2.domain.FetchOnlineUsersUseCase
import com.dongchyeon.simplechatapp_v2.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val fetchOnlineUsersUseCase: FetchOnlineUsersUseCase
) : BaseViewModel() {

    private lateinit var userList: List<User>
    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    init {
        getOnlineUsers()
    }

    fun getOnlineUsers() = viewModelScope.launch {
        fetchOnlineUsersUseCase().collect { users ->
            users.onSuccess {
                userList = it.users
                _users.postValue(userList)
                Log.d("fetchOnlineUsers", it.toString())
            }.onFailure {
                Log.d("error", it.toString())
            }
        }
    }

}