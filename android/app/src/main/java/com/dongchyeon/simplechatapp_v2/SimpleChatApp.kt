package com.dongchyeon.simplechatapp_v2

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.socket.client.IO
import io.socket.client.Socket

@HiltAndroidApp
class SimpleChatApp : Application() {

    companion object {
        val socket: Socket = IO.socket(BuildConfig.SERVER_URL)

        var TOKEN: String = ""
        lateinit var UID: String
    }

}