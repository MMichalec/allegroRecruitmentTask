package com.mmichalec.allegroRecruitmentTask.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.mmichalec.allegroRecruitmentTask.api.RepositoriesApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepoRepository @Inject constructor(private val repositoriesApi: RepositoriesApi) {

    fun getSearchResult(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { RepoPagingSource(repositoriesApi, query)}
        //TODO Try using Flow
        ).liveData

    suspend fun getRepoDetail(repoName:String) = repositoriesApi.getRepo(repoName)

}