package com.mmichalec.allegroRecruitmentTask.data

import androidx.room.Database
import androidx.room.RoomDatabase
//Repo::class is a table, we input array of tables into DB
@Database(entities = [Repo::class], version = 1)
//needs to extend RoomDatabase so it can't be an interface
abstract class RepoDatabase : RoomDatabase() {

    abstract fun repoDao(): RepoDao
}