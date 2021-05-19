package com.mmichalec.allegroRecruitmentTask.di

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.mmichalec.allegroRecruitmentTask.api.RepositoriesApi
import com.mmichalec.allegroRecruitmentTask.data.RepoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

//Class containing our dependencies

@Module
@InstallIn(SingletonComponent::class) //provides scope for dependency

object AppModule {
    @Provides // function that provides dependency
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(RepositoriesApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideRepositoriesApi(retrofit: Retrofit): RepositoriesApi =
        retrofit.create(RepositoriesApi::class.java)


    @Provides
    @Singleton
    fun provideDatabase(app:Application) : RepoDatabase =
        Room.databaseBuilder(app,RepoDatabase::class.java, "repo_database").build()

    //It is highly reccomended to use this dependency to have access to App context in any place in application
//    @Singleton
//    @Provides
//    fun provideAppliaction(@ApplicationContext app: Context) : AllegroRecruitmentTaskApp{
//        return app as AllegroRecruitmentTaskApp
//    }

}