package com.example.github.di

import android.app.Application
import com.example.android_remote.dao.RepoDao
import com.example.android_remote.dao.SearchDao
import com.example.android_remote.dao.UserDao
import com.example.android_remote.database.GitHubDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object MyCardsDatabaseModule {

    @Provides
    @Singleton
    fun providesRepoDao(gitHubDataBase: GitHubDataBase) : RepoDao{
        return gitHubDataBase.repoDao()
    }

    @Provides
    @Singleton
    fun providesSearchDao(gitHubDataBase: GitHubDataBase) : SearchDao{
        return gitHubDataBase.searchDao()
    }

    @Provides
    @Singleton
    fun providesUserDao(gitHubDataBase: GitHubDataBase) : UserDao{
        return gitHubDataBase.userDao()
    }

    @Provides
    @Singleton
    fun providesDB(application: Application): GitHubDataBase {
        return GitHubDataBase(application)
    }
}