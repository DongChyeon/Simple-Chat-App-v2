package com.dongchyeon.simplechatapp_v2

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SimpleChatApp : Application() {

    companion object {
        lateinit var token: String
        lateinit var uid: String
    }

}