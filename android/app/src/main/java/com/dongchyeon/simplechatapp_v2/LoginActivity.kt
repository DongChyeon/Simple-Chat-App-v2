package com.dongchyeon.simplechatapp_v2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dongchyeon.simplechatapp_v2.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}