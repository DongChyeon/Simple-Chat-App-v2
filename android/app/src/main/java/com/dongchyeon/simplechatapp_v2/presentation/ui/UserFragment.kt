package com.dongchyeon.simplechatapp_v2.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dongchyeon.simplechatapp_v2.R
import com.dongchyeon.simplechatapp_v2.databinding.FragmentUserBinding
import com.dongchyeon.simplechatapp_v2.presentation.adapter.UserAdapter
import com.dongchyeon.simplechatapp_v2.presentation.ui.base.BaseFragment
import com.dongchyeon.simplechatapp_v2.presentation.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFragment : BaseFragment<FragmentUserBinding>(R.layout.fragment_user) {
    private val userViewModel: UserViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind {
            viewModel = userViewModel
        }

        val adapter = UserAdapter(requireContext())

        userViewModel.users.observe(requireActivity()) { data ->
            adapter.submitList(data)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        val dividerItemDecoration =
            DividerItemDecoration(
                binding.recyclerView.context,
                LinearLayoutManager(requireContext()).orientation
            )
        binding.recyclerView.addItemDecoration(dividerItemDecoration)
    }
}