package com.dongchyeon.simplechatapp_v2.presentation.ui

import TakePictureFromCameraOrGallery
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.dongchyeon.simplechatapp_v2.R
import com.dongchyeon.simplechatapp_v2.SimpleChatApp.Companion.UID
import com.dongchyeon.simplechatapp_v2.databinding.FragmentProfileBinding
import com.dongchyeon.simplechatapp_v2.presentation.base.BaseFragment
import com.dongchyeon.simplechatapp_v2.presentation.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {
    private val profileViewModel: ProfileViewModel by viewModels()

    private lateinit var profileImgPath: String
    private var newImg by Delegates.notNull<Boolean>()

    // 이미지를 카메라 또는 앨범에서 가져와 이미지뷰에 표시
    private val getImageContent =
        registerForActivityResult(TakePictureFromCameraOrGallery()) { result: String? ->
            if (result != null) {
                profileImgPath = result
                newImg = true
                Glide
                    .with(this)
                    .load(result)
                    .circleCrop()
                    .placeholder(R.drawable.camera)
                    .into(binding.profileImgView)
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind {
            viewModel = profileViewModel
        }

        newImg = false

        profileViewModel.profile.observe(requireActivity()) { profile ->
            if (!isAdded) return@observe
            binding.nameText.text = profile.name
            binding.introMsgText.text = profile.introMsg
            profileImgPath = profile.profileImg
            Glide
                .with(requireActivity())
                .load(profile.profileImg)
                .circleCrop()
                .into(binding.profileImgView)
        }

        profileViewModel.mode.observe(requireActivity()) { mode ->
            if (!isAdded) return@observe
            if (mode.equals("VIEW")) {
                profileViewModel.getProfile(UID)

                binding.nameText.visibility = View.VISIBLE
                binding.introMsgText.visibility = View.VISIBLE
                binding.editBtn.visibility = View.VISIBLE

                binding.nameEdit.visibility = View.GONE
                binding.introMsgEdit.visibility = View.GONE
                binding.profileImgEditBtn.visibility = View.INVISIBLE
                binding.confirmBtn.visibility = View.GONE
            } else if (mode.equals("EDIT")) {
                binding.nameEdit.visibility = View.VISIBLE
                binding.introMsgEdit.visibility = View.VISIBLE
                binding.profileImgEditBtn.visibility = View.VISIBLE
                binding.confirmBtn.visibility = View.VISIBLE

                binding.nameText.visibility = View.GONE
                binding.introMsgText.visibility = View.GONE
                binding.editBtn.visibility = View.GONE
                binding.nameEdit.setText(binding.nameText.text.toString())
                binding.introMsgEdit.setText(binding.introMsgText.text.toString())
            }
        }

        binding.profileImgEditBtn.setOnClickListener {
            getImageContent.launch(Unit)
        }

        binding.confirmBtn.setOnClickListener {
            if (newImg) {
                profileViewModel.updateProfileWithImg(
                    binding.nameEdit.text.toString(),
                    binding.introMsgEdit.text.toString(),
                    profileImgPath
                )
            } else {
                profileViewModel.updateProfile(
                    binding.nameEdit.text.toString(),
                    binding.introMsgEdit.text.toString()
                )
            }
        }

        profileViewModel.toastMessage.observe(requireActivity()) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }
}