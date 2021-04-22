package com.mmichalec.allegroRecruitmentTask.ui.repoDetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.mmichalec.allegroRecruitmentTask.R
import com.mmichalec.allegroRecruitmentTask.databinding.FragmentDetailsBinding

class RepoDetailsFragment: Fragment(R.layout.fragment_details) {

    private val args by navArgs<RepoDetailsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentDetailsBinding.bind(view)

        binding.apply {
            //textViewRepoName.text = args.repoName

            textViewRepoName.apply {
                text = "Repository name: ${args.repoName}"
            }
        }
    }
}