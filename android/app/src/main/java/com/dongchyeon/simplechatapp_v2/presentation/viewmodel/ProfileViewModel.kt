package com.dongchyeon.simplechatapp_v2.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dongchyeon.simplechatapp_v2.SimpleChatApp.Companion.UID
import com.dongchyeon.simplechatapp_v2.data.model.User
import com.dongchyeon.simplechatapp_v2.data.model.request.ProfileNoImgReq
import com.dongchyeon.simplechatapp_v2.domain.GetProfileUseCase
import com.dongchyeon.simplechatapp_v2.domain.UpdateProfileUseCase
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
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase
) : ViewModel() {
    private val _mode = MutableLiveData<String>("VIEW")
    val mode: LiveData<String> = _mode

    private lateinit var user: User
    private val _profile = MutableLiveData<User>()
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

    fun updateProfile(
        name: String,
        introMsg: String
    ) = viewModelScope.launch {
        updateProfileUseCase.updateProfile(ProfileNoImgReq(UID, name, introMsg)).onSuccess {
            _mode.postValue("VIEW")
            Log.d("updateProfile", it.toString())
        }.onFailure {
            Log.d("error", it.toString())
        }
    }

    fun updateProfileWithImg(
        name: String,
        introMsg: String,
        profileImgPath: String
    ) = viewModelScope.launch {
        val image = File(profileImgPath)
        val requestFile = image.asRequestBody("image/*".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("profile_img", image.name, requestFile)

        val data = HashMap<String, RequestBody>()
        data["id"] = UID.toRequestBody("text/plain".toMediaTypeOrNull())
        data["name"] = name.toRequestBody("text/plain".toMediaTypeOrNull())
        data["intro_msg"] = introMsg.toRequestBody("text/plain".toMediaTypeOrNull())

        updateProfileUseCase.updateProfileWithImg(data, body).onSuccess {
            _mode.postValue("VIEW")
            Log.d("updateProfile", it.toString())
        }.onFailure {
            Log.d("error", it.toString())
        }
    }

    fun setEditMode() {
        _mode.postValue("EDIT")
    }

}