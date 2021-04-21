package com.mmichalec.allegroRecruitmentTask.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mmichalec.allegroRecruitmentTask.api.RepositoriesApi
import retrofit2.HttpException
import java.io.IOException


private const val REPO_STARTING_PAGE_INDEX = 1
class RepoPagingSource(
    private val repositoriesApi: RepositoriesApi,
    private val query: String
): PagingSource<Int, Repo>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repo> {
        val position = params.key ?: REPO_STARTING_PAGE_INDEX


        return try {


            val response = repositoriesApi.searchRepo(query, position, params.loadSize)
            val repos = response

            LoadResult.Page(
                data = repos,
                prevKey = if (position == REPO_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if(repos.isEmpty()) null else position + 1
            )
        }catch (exception: IOException){
            LoadResult.Error(exception)
        } catch (exception: HttpException){
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Repo>): Int? {
        TODO("Not yet implemented")
    }
}