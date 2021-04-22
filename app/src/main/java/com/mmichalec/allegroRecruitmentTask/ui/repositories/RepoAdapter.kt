package com.mmichalec.allegroRecruitmentTask.ui.repositories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mmichalec.allegroRecruitmentTask.data.Repo
import com.mmichalec.allegroRecruitmentTask.databinding.ItemRepositoryBinding
import java.time.ZonedDateTime

class RepoAdapter : PagingDataAdapter<Repo, RepoAdapter.RepoViewHolder>(REPO_COMPARATOR) {




    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val binding =
            ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepoViewHolder(binding)
    }

    //nested class
    class RepoViewHolder(private val binding: ItemRepositoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(repo: Repo) {
            binding.apply {
                textViewName.text = repo.name
                if(repo.description !=null){
                    textViewDescription.text = repo.description
                }else {
                    textViewDescription.text = "<no description>"
                }
                textViewDateOfCreation.text = "Created: ${ZonedDateTime.parse(repo.created_at).dayOfMonth.toString()} ${ZonedDateTime.parse(repo.created_at).month.toString()} ${ZonedDateTime.parse(repo.created_at).year.toString()}"
                textViewDateOfLastUpdate.text =  "Updated: ${ZonedDateTime.parse(repo.updated_at).dayOfMonth.toString()} ${ZonedDateTime.parse(repo.updated_at).month.toString()} ${ZonedDateTime.parse(repo.updated_at).year.toString()}"
            }
        }
    }



    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Repo>() {
            override fun areItemsTheSame(oldItem: Repo, newItem: Repo) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Repo, newItem: Repo) =
                oldItem == newItem

        }
    }
}