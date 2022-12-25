package com.dongchyeon.simplechatapp_v2.presentation.ui

import TakePictureFromCameraOrGallery
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.dongchyeon.simplechatapp_v2.R
import com.dongchyeon.simplechatapp_v2.databinding.ActivitySignupBinding
import com.dongchyeon.simplechatapp_v2.presentation.base.BaseActivity
import com.dongchyeon.simplechatapp_v2.presentation.viewmodel.SignupViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupActivity : BaseActivity<ActivitySignupBinding>(R.layout.activity_signup) {
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
        bind {
            viewModel = signupViewModel
        }

        binding.registerBtn.setOnClickListener {
            signupViewModel.signup(
                binding.idEdit.text.toString(),
                binding.pwEdit.text.toString(),
                binding.nameEdit.text.toString(),
                binding.introMsgEdit.text.toString(),
                profileImgPath
            )
        }

        signupViewModel.isSignedUp.observe(this) { success ->
            if (success) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }

        binding.selectImg.setOnClickListener {
            getImageContent.launch(Unit)
        }

        signupViewModel.toastMessage.observe(this) {
            Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
        }
    }
}