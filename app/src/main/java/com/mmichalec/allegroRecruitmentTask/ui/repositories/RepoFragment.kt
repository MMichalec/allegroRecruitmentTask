package com.mmichalec.allegroRecruitmentTask.ui.repositories

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mmichalec.allegroRecruitmentTask.R
import com.mmichalec.allegroRecruitmentTask.data.Repo
import com.mmichalec.allegroRecruitmentTask.databinding.FragmentRepoListBinding
import com.mmichalec.allegroRecruitmentTask.util.NetworkConnection
import com.mmichalec.allegroRecruitmentTask.util.Resource

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import java.lang.Runnable

@AndroidEntryPoint // marking class for dependency injection
class RepoFragment : Fragment(R.layout.fragment_repo_list),
    RepoAdapter.OnItemClickListener {

    private var isConnection = false
    private val viewModel by viewModels<RepoViewModel>()
    private var _binding: FragmentRepoListBinding? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val networkConnection = NetworkConnection(activity?.applicationContext!!)
        networkConnection.observe(viewLifecycleOwner, Observer { isConnected ->
            isConnection = isConnected
            if (!isConnected) {
                noNetworkPopup()
            }
        })

        val binding = FragmentRepoListBinding.bind(view)
        _binding = binding

        val repoAdapter = RepoAdapter(this)

        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.addItemDecoration(
                DividerItemDecoration(
                    parentFragment?.context,
                    DividerItemDecoration.VERTICAL
                )
            )

            recyclerView.apply {
                adapter = repoAdapter
                layoutManager = LinearLayoutManager(this@RepoFragment.context)
            }

            viewModel.repositories.observe(viewLifecycleOwner) {
                repoAdapter.submitList(it.data)
                //TODO: Fix scrolling to position 0. Also get rid of Handlers in onMenuOptionClick
                //recyclerView.smoothScrollToPosition(0)
                progressBar.isVisible = it is Resource.Loading && it.data.isNullOrEmpty()
                textViewError.isVisible = it is Resource.Error && it.data.isNullOrEmpty()
                textViewError.text = it.error?.localizedMessage

                if(textViewError.text.contains("403")){
                    textViewError.text = "${it.error?.localizedMessage} \n There is a possibility that maximum number of API calls from your IP address has been exceeded. For more information check official Githgub REST Api documentation."
                }
            }
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.repo_list_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        //TODO: After fixing shouldFetch in RepoRepository class change searching.
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchQuery.value = query!!
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    if(newText.isEmpty()){
                        viewModel.searchQuery.value = newText
                    }
                }
                return true
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.action_sort_by_name -> {
                viewModel.sortOrder.value = RepoViewModel.SortOrder.BY_NAME
                Handler().postDelayed({ _binding?.recyclerView?.scrollToPosition(0) }, 300)
                true
            }

            R.id.action_sort_by_created_date -> {
                viewModel.sortOrder.value = RepoViewModel.SortOrder.BY_CREATION_DATE
                Handler().postDelayed({ _binding?.recyclerView?.scrollToPosition(0) }, 300)
                true
            }

            R.id.action_sort_by_update_date -> {

                viewModel.sortOrder.value = RepoViewModel.SortOrder.BY_UPDATE_DATE
                Handler().postDelayed({ _binding?.recyclerView?.scrollToPosition(0) }, 300)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }

    override fun onItemClick(repo: Repo) {

        if (isConnection) {
            val action =
                RepoFragmentDirections.actionRepositoriesFragmentToRepoDetailsFragment(
                    repo.name
                )
            findNavController().navigate(action)
        } else {
            showInfoDialog("Application is working in offline mode.\n\nYou need online connection to be able to see repository ${repo.name} details.")
        }
    }

    private fun showInfoDialog(message: String) {
        val builder = AlertDialog.Builder(this@RepoFragment.context)
        builder.setTitle("WARNING")
        builder.setMessage(message)

        builder.setPositiveButton("Dismiss") { dialogInterface: DialogInterface, i: Int ->
        }
        builder.show()
    }

    private fun noNetworkPopup() {
        activity?.runOnUiThread(object : Runnable {
            override fun run() {
                showInfoDialog("Application is working in an offline mode.\n\nIf this is your first use of the application you will not see any repositories as the data could not be loaded.\n\nIf it's not your first use the application will use cached data to display repositories but the data might be outdated.")
            }
        })
// Better way with the use of coroutine
//        private fun noNetworkPopup() {
//            CoroutineScope(Dispatchers.Main).launch {
//                showInfoDialog("Application is working in an offline mode.\n\nIf this is your first use of the application you will not see any repositories as the data could not be loaded.\n\nIf it's not your first use the application will use cached data to display repositories but the data might be outdated.")
//            }
//        }
    }


}