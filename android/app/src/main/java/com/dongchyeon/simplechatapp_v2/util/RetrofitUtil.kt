package com.dongchyeon.simplechatapp_v2.util

import okhttp3.MediaType
import okhttp3.RequestBody

object Util {
    fun getRequestBodyFromString(string: String): RequestBody {
        return RequestBody.create(MediaType.parse("text/plain"), string)
    }
}