package com.dongchyeon.simplechatapp_v2.presentation.ui

import TakePictureFromCameraOrGallery
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.dongchyeon.simplechatapp_v2.R
import com.dongchyeon.simplechatapp_v2.databinding.ActivitySignupBinding
import com.dongchyeon.simplechatapp_v2.presentation.viewmodel.SignupViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private val signupViewModel: SignupViewModel by viewModels()

    private var profileImgPath: String = ""

    // 이미지를 카메라 또는 앨범에서 가져와 이미지뷰에 표시
    private val getImageContent =
        registerForActivityResult(TakePictureFromCameraOrGallery()) { result: String? ->
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
        binding = DataBindingUtil.setContentView(this@SignupActivity, R.layout.activity_signup)
        binding.apply {
            lifecycleOwner = this@SignupActivity
            viewModel = signupViewModel
        }

        binding.registerBtn.setOnClickListener {
            val idText = binding.idEdit.text.toString()
            val pwText = binding.pwEdit.text.toString()
            val nameText = binding.nameEdit.text.toString()
            val introMsgText = binding.introMsgEdit.text.toString()

            if (idText == "") {
                Toast.makeText(applicationContext, "ID는 필수 입력 항목입니다.", Toast.LENGTH_SHORT).show()
            } else if (pwText == "") {
                Toast.makeText(applicationContext, "비밀번호는 필수 입력 항목입니다.", Toast.LENGTH_SHORT).show()
            } else if (nameText == "") {
                Toast.makeText(applicationContext, "이름은 필수 입력 항목입니다.", Toast.LENGTH_SHORT).show()
            } else if (introMsgText == "") {
                Toast.makeText(applicationContext, "소개 메시지는 필수 입력 항목입니다.", Toast.LENGTH_SHORT)
                    .show()
            } else if (profileImgPath == "") {
                Toast.makeText(applicationContext, "프로필 사진은 설정하세요.", Toast.LENGTH_SHORT).show()
            } else {
                signupViewModel.signup(
                    idText,
                    pwText,
                    nameText,
                    introMsgText,
                    profileImgPath
                )
            }
        }

        signupViewModel.isSignedUp.observe(this) { success ->
            if (success) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                Toast.makeText(applicationContext, "회원가입 실패", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        binding.selectImg.setOnClickListener {
            getImageContent.launch(Unit)
        }
    }
}