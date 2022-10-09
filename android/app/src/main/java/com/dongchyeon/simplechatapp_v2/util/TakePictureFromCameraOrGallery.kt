package com.dongchyeon.simplechatapp_v2.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContract
import androidx.core.content.FileProvider
import com.dongchyeon.simplechatapp_v2.BuildConfig
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class TakePictureFromCameraOrGallery : ActivityResultContract<Unit, Uri?>() {
    private var photoUri : Uri? = null

    override fun createIntent(context: Context, input: Unit): Intent {
        return openImageIntent(context)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
        if (resultCode != Activity.RESULT_OK) return null
        return intent?.data
    }

    private fun openImageIntent(context : Context) : Intent {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        photoUri = getUriFromTakenPhoto(context)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)

        val galleryIntent = Intent(Intent.ACTION_GET_CONTENT)
        galleryIntent.type = "image/*"

        val packageManger = context.packageManager

        cameraIntent.component = cameraIntent.resolveActivity(packageManger)
        galleryIntent.component = galleryIntent.resolveActivity(packageManger)

        val intentList = arrayListOf<Intent>(cameraIntent, galleryIntent)

        val chooser = Intent.createChooser(galleryIntent, "이미지를 불러올 앱을 선택해주세요.")
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentList.toTypedArray())

        return chooser
    }

    private fun createFile(context : Context) : File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.KOREA).format(Date())
        val imageFileName = "IMG_" + timeStamp + "_"
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) ?: throw IllegalStateException("Dir not found")
        return File.createTempFile(
            imageFileName,
            ".jpg",
            storageDir
        )
    }

    private fun getUriFromTakenPhoto(context : Context) : Uri {
        val file = createFile(context)

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileProvider", file)
        } else {
            Uri.fromFile(file)
        }
    }
}