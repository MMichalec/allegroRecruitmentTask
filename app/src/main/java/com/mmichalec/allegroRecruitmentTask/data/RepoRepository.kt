package com.mmichalec.allegroRecruitmentTask.data

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import androidx.room.withTransaction
import com.mmichalec.allegroRecruitmentTask.api.RepositoriesApi
import com.mmichalec.allegroRecruitmentTask.ui.repositories.RepoViewModel
import com.mmichalec.allegroRecruitmentTask.util.networkBoundResource
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepoRepository @Inject constructor(
    private val repositoriesApi: RepositoriesApi, private val db : RepoDatabase
    ) {

    private val repoDao = db.repoDao()
//    private var time = 0L
    suspend fun getRepoDetail(repoName:String) = repositoriesApi.getRepo(repoName)

    fun getAllRepos(searchQuery: String, sortOrder:RepoViewModel.SortOrder) = networkBoundResource(
        //passing functions as lambda
        query = {
            repoDao.getRepos(searchQuery, sortOrder)
        },
        fetch = {
//            time = System.currentTimeMillis()
            Log.d("Repository","Api called")
           repositoriesApi.getReposFromApi()
        },
        //TODO: Fix should fetch.
//        shouldFetch = {
//            System.currentTimeMillis() - time > 60000
//        },
        saveFetchResult = {
            //transactions means that all must be executed. If one fails none will happen
            db.withTransaction {
                repoDao.deleteAllRepos()
                repoDao.insertRepos(it)
            }
        }
    )

}