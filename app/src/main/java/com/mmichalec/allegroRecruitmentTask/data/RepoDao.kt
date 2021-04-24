package com.mmichalec.allegroRecruitmentTask.data

import androidx.paging.PagingData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mmichalec.allegroRecruitmentTask.ui.repositories.RepositoriesViewModel
import kotlinx.coroutines.flow.Flow

@Dao
interface RepoDao {

    fun getRepos(query:String, sortOrder: RepositoriesViewModel.SortOrder): Flow<List<Repo>> =
        when(sortOrder){
            RepositoriesViewModel.SortOrder.BY_NAME -> getReposSortedByName(query)
            RepositoriesViewModel.SortOrder.BY_CREATION_DATE -> getReposSortedByDateCreated(query)
            RepositoriesViewModel.SortOrder.BY_UPDATE_DATE -> getReposSortedByDateUpdated(query)
        }

    @Query("SELECT * FROM repositories WHERE name LIKE '%' || :searchQuery || '%' ORDER by LOWER(name) ASC, name ")
    fun getReposSortedByName(searchQuery: String): Flow<List<Repo>>

    @Query("SELECT * FROM repositories WHERE name LIKE '%' || :searchQuery || '%' ORDER by created_at DESC, created_at ")
    fun getReposSortedByDateCreated(searchQuery: String): Flow<List<Repo>>

    @Query("SELECT * FROM repositories WHERE name LIKE '%' || :searchQuery || '%' ORDER by updated_at DESC, updated_at ")
    fun getReposSortedByDateUpdated(searchQuery: String): Flow<List<Repo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepos(repos : List<Repo>)

    @Query("DELETE FROM  repositories")
    suspend fun deleteAllRepos(){

    }
}