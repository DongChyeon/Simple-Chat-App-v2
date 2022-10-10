package com.dongchyeon.simplechatapp_v2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.dongchyeon.simplechatapp_v2.databinding.ActivityLoginBinding
import com.dongchyeon.simplechatapp_v2.retrofit.RetrofitClient
import com.dongchyeon.simplechatapp_v2.retrofit.dto.request.LoginReqDto
import com.dongchyeon.simplechatapp_v2.retrofit.dto.response.LoginResDto
import com.dongchyeon.simplechatapp_v2.retrofit.dto.response.SignupResDto
import com.dongchyeon.simplechatapp_v2.retrofit.service.LoginService
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // 다크모드 비활성화
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val retrofitClient = RetrofitClient.getInstance().create(LoginService::class.java)

        binding.loginBtn.setOnClickListener {
            val loginReqDto = LoginReqDto(binding.idEdit.text.toString(), binding.pwEdit.text.toString())

            retrofitClient.login(loginReqDto).enqueue(object :
                Callback<LoginResDto> {
                override fun onResponse(
                    call : Call<LoginResDto>,
                    response : Response<LoginResDto>
                ) {
                    if (response.isSuccessful) {
                        Log.d("login", response.body().toString())
                        Toast.makeText(applicationContext, response.body()!!.message, Toast.LENGTH_LONG).show()
                    } else {
                        val loginErrorDto = Gson().fromJson(response.errorBody()?.string()!!, LoginResDto::class.java)
                        Log.d("login", loginErrorDto.message)
                        Toast.makeText(applicationContext, loginErrorDto.message, Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call : Call<LoginResDto>, t: Throwable) {
                    Log.d("login", t.toString())
                    Toast.makeText(applicationContext, "로그인 오류 발생", Toast.LENGTH_LONG).show()
                }
            })
        }

        binding.signupBtn.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }
}
