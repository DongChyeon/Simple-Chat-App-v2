package com.dongchyeon.simplechatapp_v2.util

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import okhttp3.MediaType
import okhttp3.RequestBody

object Util {
    const val PERMISSION_SELECT_IMAGE = 100

    fun getRealPathFromURI(path: Uri, context: Context): String {
        val proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        val c: Cursor? = context.contentResolver.query(path, proj, null, null, null)
        val index = c?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c?.moveToFirst()

        val result = c?.getString(index!!)

        return result!!
    }

    fun getRequestBodyFromString(string: String): RequestBody {
        return RequestBody.create(MediaType.parse("text/plain"), string)
    }
}