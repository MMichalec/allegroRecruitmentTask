package com.mmichalec.allegroRecruitmentTask.data

import androidx.paging.PagingData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RepoDao {

    @Query("SELECT * FROM repositories WHERE name LIKE '%' || :searchQuery || '%' ORDER by name DESC ")
    fun getAllRepos(searchQuery: String): Flow<List<Repo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepos(repos : List<Repo>)

    @Query("DELETE FROM  repositories")
    suspend fun deleteAllRepos(){

    }
}