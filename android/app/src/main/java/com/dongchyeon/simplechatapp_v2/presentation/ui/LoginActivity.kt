package com.dongchyeon.simplechatapp_v2.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.dongchyeon.simplechatapp_v2.R
import com.dongchyeon.simplechatapp_v2.databinding.ActivityLoginBinding
import com.dongchyeon.simplechatapp_v2.presentation.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@LoginActivity, R.layout.activity_login)
        binding.apply {
            lifecycleOwner = this@LoginActivity
            viewModel = loginViewModel
        }

        // 다크모드 비활성화
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding.signupBtn.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
        binding.loginBtn.setOnClickListener {
            loginViewModel.login(binding.idEdit.text.toString(), binding.pwEdit.text.toString())
        }

        loginViewModel.isLoggedIn.observe(this) { success ->
            if (success) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(applicationContext, "아이디와 비밀번호를 다시 확인해주세요.", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}