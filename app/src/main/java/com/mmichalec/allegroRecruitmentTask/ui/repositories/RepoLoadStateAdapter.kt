package com.mmichalec.allegroRecruitmentTask.ui.repositories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mmichalec.allegroRecruitmentTask.databinding.RepoLoadStateFooterBinding

class RepoLoadStateAdapter(private val retry: () -> Unit): LoadStateAdapter<RepoLoadStateAdapter.LoadStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding = RepoLoadStateFooterBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return LoadStateViewHolder(binding)
    }

    inner class LoadStateViewHolder(private val binding: RepoLoadStateFooterBinding):
            RecyclerView.ViewHolder(binding.root){

        init {
            binding.buttonRetry.setOnClickListener{
                retry.invoke()
            }
        }

        fun bind(loadState: LoadState){
            binding.apply {
                progressBar.isVisible = loadState is LoadState.Loading
                buttonRetry.isVisible = loadState !is LoadState.Loading
                textViewError.isVisible = loadState !is LoadState.Loading
            }
        }
    }
}