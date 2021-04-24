package com.mmichalec.allegroRecruitmentTask.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Repo::class], version = 2)
abstract class RepoDatabase : RoomDatabase() {

    abstract fun repoDao(): RepoDao
}