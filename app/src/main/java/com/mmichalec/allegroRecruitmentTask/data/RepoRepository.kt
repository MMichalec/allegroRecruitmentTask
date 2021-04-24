package com.mmichalec.allegroRecruitmentTask.data

import androidx.lifecycle.asFlow
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import androidx.room.RoomDatabase
import com.mmichalec.allegroRecruitmentTask.api.RepositoriesApi
import com.mmichalec.allegroRecruitmentTask.util.networkBoundResource
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepoRepository @Inject constructor(
    private val repositoriesApi: RepositoriesApi, private val db : RepoDatabase
    ) {

    private val repoDao = db.repoDao()

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

    fun getAllRepos(searchQuery: String) = networkBoundResource(
        query = {

            repoDao.getAllRepos(searchQuery)
        },
        fetch = {
           repositoriesApi.getReposFromApi()
        },
        saveFetchResult = {
            repoDao.deleteAllRepos()
            repoDao.insertRepos(it)
        }
    )

}