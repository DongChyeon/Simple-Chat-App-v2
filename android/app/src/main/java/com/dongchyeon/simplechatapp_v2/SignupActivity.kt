package com.dongchyeon.simplechatapp_v2

import TakePictureFromCameraOrGallery
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.dongchyeon.simplechatapp_v2.util.Util.getRequestBodyFromString

import com.dongchyeon.simplechatapp_v2.databinding.ActivitySignupBinding
import com.dongchyeon.simplechatapp_v2.retrofit.RetrofitClient
import com.dongchyeon.simplechatapp_v2.retrofit.dto.response.SignupResDto
import com.dongchyeon.simplechatapp_v2.retrofit.service.SignupService

import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import kotlin.collections.HashMap

class SignupActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySignupBinding
    private lateinit var profileImgPath : String
    // 이미지를 카메라 또는 앨범에서 가져와 이미지뷰에 표시
    private val getImageContent = registerForActivityResult(TakePictureFromCameraOrGallery()) { result: String? ->
        if (result != null) {
            profileImgPath = result
            Glide
                .with(this)
                .load(result)
                .circleCrop()
                .placeholder(R.drawable.camera)
                .into(binding.selectImg)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val retrofitClient = RetrofitClient.getInstance().create(SignupService::class.java)

        binding.registerBtn.setOnClickListener {
            val id = binding.idEdit.text.toString()
            val password = binding.pwEdit.text.toString()
            val name = binding.nameEdit.text.toString()

            val image = File(profileImgPath)
            val requestFile = RequestBody.create(MediaType.parse("image/*"), image)
            val body = MultipartBody.Part.createFormData("profile_img", image.name, requestFile)

            val data = HashMap<String, RequestBody>()
            data["id"] = getRequestBodyFromString(id)
            data["password"] = getRequestBodyFromString(password)
            data["name"] = getRequestBodyFromString(name)

            retrofitClient.signup(data, body)
                .enqueue(object : Callback<SignupResDto> {
                override fun onResponse(
                    call: Call<SignupResDto>,
                    response: Response<SignupResDto>
                ) {
                    if (response.isSuccessful) {
                        Log.d("signup", response.body().toString())
                        Toast.makeText(applicationContext, response.body()!!.message, Toast.LENGTH_LONG).show()
                    } else {
                        val signupErrorDto = Gson().fromJson(response.errorBody()?.string()!!, SignupResDto::class.java)
                        Log.d("signup", signupErrorDto.message)
                        Toast.makeText(applicationContext, signupErrorDto.message, Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<SignupResDto>, t: Throwable) {
                    Log.d("signup", t.toString())
                    Toast.makeText(applicationContext, "회원가입 오류 발생", Toast.LENGTH_LONG).show()
                }
            })
        }

        binding.selectImg.setOnClickListener {
            getImageContent.launch(Unit)
        }
    }
}