package com.mmichalec.allegroRecruitmentTask.ui.repositories

import android.app.Application
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
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
            recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                header = RepoLoadStateAdapter{adapter.retry()},
                footer = RepoLoadStateAdapter{adapter.retry()},
            )
        }

        viewModel.repos.observe(viewLifecycleOwner){
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.repo_list_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query != null){
                    binding.recyclerView.scrollToPosition(0)
                    viewModel.searchRepos(query)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_sort_by_name ->{
                viewModel.setSortByName()
                binding.recyclerView.scrollToPosition(0)
                true
            }

            R.id.action_sort_by_created_date ->{
                viewModel.setSortByCreationDate()
                binding.recyclerView.scrollToPosition(0)
                true
            }

            R.id.action_sort_by_update_date ->{
                viewModel.setSortByLastUpdateDate()
                binding.recyclerView.scrollToPosition(0)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}