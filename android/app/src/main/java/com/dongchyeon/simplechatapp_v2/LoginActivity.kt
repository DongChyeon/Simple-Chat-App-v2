package com.dongchyeon.simplechatapp_v2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.dongchyeon.simplechatapp_v2.databinding.ActivityLoginBinding
import com.dongchyeon.simplechatapp_v2.retrofit.RetrofitClient
import com.dongchyeon.simplechatapp_v2.retrofit.dto.request.LoginRequestDto
import com.dongchyeon.simplechatapp_v2.retrofit.dto.response.LoginResponseDto
import com.dongchyeon.simplechatapp_v2.retrofit.service.LoginService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val retrofitClient = RetrofitClient.getInstance().create(LoginService::class.java)

        binding.loginBtn.setOnClickListener {
            val loginRequestDto = LoginRequestDto(binding.idEdit.text.toString(), binding.pwEdit.text.toString())

            retrofitClient.login(loginRequestDto).enqueue(object : Callback<LoginResponseDto> {
                override fun onResponse(call : Call<LoginResponseDto>, response : Response<LoginResponseDto>) {
                    if (response.isSuccessful) Log.d("login", response.body().toString())
                    else if (response.code() == 401) Log.d("login", "로그인에 실패했습니다.");
                    else Log.d("login", "로그인에 실패했습니다.")
                }

                override fun onFailure(call : Call<LoginResponseDto>, t: Throwable) {
                    Log.d("login", t.toString())
                }
            })
        }
    }
}
