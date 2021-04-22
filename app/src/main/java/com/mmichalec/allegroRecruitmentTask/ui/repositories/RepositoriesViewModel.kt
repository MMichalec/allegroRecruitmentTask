package com.mmichalec.allegroRecruitmentTask.ui.repositories

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.mmichalec.allegroRecruitmentTask.data.RepoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RepositoriesViewModel @Inject constructor(
    private val repository: RepoRepository) :
    ViewModel() {

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)
    val repos = currentQuery.switchMap {queryString ->
        repository.getSearchResult(queryString).cachedIn(viewModelScope)
    }

    fun searchRepos(query: String) {
        currentQuery.value = query
    }


    companion object {
        private  const val DEFAULT_QUERY = "full_name"
    }
}