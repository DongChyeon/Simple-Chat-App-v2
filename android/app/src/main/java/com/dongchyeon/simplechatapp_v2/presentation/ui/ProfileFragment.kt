package com.dongchyeon.simplechatapp_v2.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.dongchyeon.simplechatapp_v2.R
import com.dongchyeon.simplechatapp_v2.databinding.FragmentProfileBinding
import com.dongchyeon.simplechatapp_v2.presentation.ui.base.BaseFragment
import com.dongchyeon.simplechatapp_v2.presentation.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {
    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind {
            viewModel = profileViewModel
        }

        profileViewModel.profile.observe(requireActivity()) { data ->
            binding.nameText.text = data.name
            binding.introMsgText.text = data.introMsg
            Glide
                .with(requireActivity())
                .load(data.profileImg)
                .circleCrop()
                .into(binding.profileImgView)
        }
    }
}