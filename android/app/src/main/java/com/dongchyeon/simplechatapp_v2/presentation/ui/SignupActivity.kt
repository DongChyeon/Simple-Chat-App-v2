package com.dongchyeon.simplechatapp_v2.presentation.ui

import TakePictureFromCameraOrGallery
import android.os.Bundle
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

    private lateinit var profileImgPath: String

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
            signupViewModel.signup(
                binding.idEdit.text.toString(),
                binding.pwEdit.text.toString(),
                binding.nameEdit.text.toString(),
                profileImgPath
            )
        }

        binding.selectImg.setOnClickListener {
            getImageContent.launch(Unit)
        }
    }
}