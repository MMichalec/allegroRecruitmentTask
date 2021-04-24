package com.mmichalec.allegroRecruitmentTask.ui.repositories

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.mmichalec.allegroRecruitmentTask.data.Repo
import com.mmichalec.allegroRecruitmentTask.data.RepoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositoriesViewModel @Inject constructor(
    private val repository: RepoRepository) :
    ViewModel() {

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)
    val repos = currentQuery.switchMap {queryString ->
        repository.getSearchResult(queryString).cachedIn(viewModelScope)
    }


    private val repoLiveData = MutableLiveData<List<Repo>>()
    val searchQuery = MutableStateFlow("")
    private val repoFlow = searchQuery.flatMapLatest {
        repository.getAllRepos(it)
    }
    val reposits = repoFlow.asLiveData()




    fun searchRepos(query: String) {
        currentQuery.value = query
    }

    fun setSortByName(){
        currentQuery.value = "full_name"
    }

    fun setSortByCreationDate(){
        currentQuery.value = "created"
    }

    fun setSortByLastUpdateDate(){
        currentQuery.value = "updated"
    }


    companion object {
        private  const val DEFAULT_QUERY = "full_name"

    }
}