package com.mmichalec.allegroRecruitmentTask.ui.repositories

import android.app.Application
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.mmichalec.allegroRecruitmentTask.R
import com.mmichalec.allegroRecruitmentTask.databinding.FragmentRepoListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepositoriesFragment: Fragment(R.layout.fragment_repo_list) {

    private  val viewModel by viewModels<RepositoriesViewModel>()

    private var _binding: FragmentRepoListBinding? = null
    private  val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentRepoListBinding.bind(view)

        val adapter = RepoAdapter()

        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.addItemDecoration(DividerItemDecoration(parentFragment?.context, DividerItemDecoration.VERTICAL))
            recyclerView.adapter = adapter
        }

        viewModel.repos.observe(viewLifecycleOwner){
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}