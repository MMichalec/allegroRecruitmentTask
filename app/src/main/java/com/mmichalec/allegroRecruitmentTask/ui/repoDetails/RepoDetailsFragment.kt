package com.mmichalec.allegroRecruitmentTask.ui.repoDetails

import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.mmichalec.allegroRecruitmentTask.R
import com.mmichalec.allegroRecruitmentTask.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepoDetailsFragment: Fragment(R.layout.fragment_details) {

    private val args by navArgs<RepoDetailsFragmentArgs>()

    private val viewModel: RepoDetailsViewModel by viewModels()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDetailsBinding.bind(view)



        binding.apply {


            textViewRepoName.apply {
                text = "Repository name: ${args.repoName}"
            }

               viewModel.repoDetails.observe(viewLifecycleOwner){

                   if(textViewUrl.text != null){
                       progressBar.visibility = View.GONE
                   }

                   textViewUrl.text = it.url
               }

            }
        }
    }
