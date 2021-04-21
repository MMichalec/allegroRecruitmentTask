package com.mmichalec.allegroRecruitmentTask.ui.repositories

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mmichalec.allegroRecruitmentTask.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepositoriesFragment: Fragment(R.layout.fragment_repo_list) {

    private  val viewModel by viewModels<RepositoriesViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.repos.observe(viewLifecycleOwner){

        }
    }

}