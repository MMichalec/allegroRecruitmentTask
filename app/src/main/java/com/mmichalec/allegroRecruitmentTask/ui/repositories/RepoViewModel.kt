package com.mmichalec.allegroRecruitmentTask.ui.repositories

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.cachedIn
import androidx.recyclerview.widget.RecyclerView
import com.mmichalec.allegroRecruitmentTask.data.Repo
import com.mmichalec.allegroRecruitmentTask.data.RepoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepoViewModel @Inject constructor(
    private val repository: RepoRepository) :
    ViewModel() {


    //Exposing data - state flow holds single value unlike flow but can be used as flow.
    val searchQuery = MutableStateFlow("")
    val sortOrder = MutableStateFlow(SortOrder.BY_NAME)

    //combine lets us combine multiple flows into one flow
    private val repoFlow = combine(
        searchQuery,
        sortOrder
    //multiple parameters for lambda must be given names
    ) {query, sortOrder ->
        //emitting Pair object whenever something changes
        Pair(query,sortOrder)
        //flatMapLatest - block inside is executed whenever value has changed. In LiveDate this operator is called switchMap
        //flattening into a single flow, latest ensures that always the latest value of all flows is shown for example if previous call in still in progress. Here it's taking latest values.
    }.flatMapLatest {(query,sortOrder) ->
        repository.getAllRepos(query,sortOrder)
    }
    /*
    turning flow into LiveData will launch a coroutine, then will collect each values from flow and will emit it (set the value of LD from Flow)
   values coming from flow are db updates. LD also caches values across config changes.
   */
        val repositories = repoFlow.asLiveData()


        enum class SortOrder {BY_NAME, BY_CREATION_DATE, BY_UPDATE_DATE}
}