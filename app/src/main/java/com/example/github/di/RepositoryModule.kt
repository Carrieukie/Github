package com.example.github.di

import com.example.android_remote.dao.RepoDao
import com.example.android_remote.dao.SearchDao
import com.example.android_remote.dao.UserDao
import com.example.android_remote.repositories.CacheRepositoryImpl
import com.example.data_remote.api.GithubApi
import com.example.data_remote.repositories.RemoteRepositoryImpl
import com.example.domain.repositories.CacheRepository
import com.example.domain.repositories.RemoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun providesCacheRepository(repoDao: RepoDao, searchDao: SearchDao, userDao: UserDao) : CacheRepository{
        return CacheRepositoryImpl(repoDao = repoDao, searchDao = searchDao, userDao = userDao)
    }

    @Singleton
    @Provides
    fun providesRemoteRepository(githubApi: GithubApi) : RemoteRepository{
        return RemoteRepositoryImpl(githubApi = githubApi)
    }

}