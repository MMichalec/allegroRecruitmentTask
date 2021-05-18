package com.mmichalec.allegroRecruitmentTask.ui.repositories

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.cachedIn
import androidx.recyclerview.widget.RecyclerView
import com.mmichalec.allegroRecruitmentTask.data.Repo
import com.mmichalec.allegroRecruitmentTask.data.RepoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepoViewModel @Inject constructor(
    private val repository: RepoRepository) :
    ViewModel() {


    val searchQuery = MutableStateFlow("")
    val sortOrder = MutableStateFlow(SortOrder.BY_NAME)

    private val repoFlow = combine(
        searchQuery,
        sortOrder

    ) {query, sortOrder ->
        Pair(query,sortOrder)

    }.flatMapLatest {(query,sortOrder) ->
        repository.getAllRepos(query,sortOrder)
    }
    /*turning flow into LiveData will launch a coroutine, then will collect each values from flow and will emit it (set the value of LD from Flow)
   values coming from flow are db updates. LD also caches values across config changes.
   */
        val repositories = repoFlow.asLiveData()


        enum class SortOrder {BY_NAME, BY_CREATION_DATE, BY_UPDATE_DATE}
}