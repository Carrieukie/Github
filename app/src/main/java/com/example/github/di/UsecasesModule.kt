package com.example.github.di

import com.example.domain.repositories.CacheRepository
import com.example.domain.repositories.RemoteRepository
import com.example.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {

    @Provides
    @Singleton
    @Named("get_followers")
    fun providesGetFollowersUseCase(remoteRepository: RemoteRepository) : GetFollowersBaseUseCase{
        return GetFollowersFromInternetUseCase(remoteRepository)
    }

    @Provides
    @Singleton
    fun providesGetFollowingUseCase(remoteRepository: RemoteRepository) : GetFollowingBaseUseCase{
        return GetFollowingFromInternetUseCase(remoteRepository)
    }

    @Provides
    @Singleton
    fun providesGetReposUseCase(remoteRepository: RemoteRepository) : GetReposFromInternetUseCase{
        return GetReposFromInternetUseCase(remoteRepository)
    }

    @Singleton
    @Provides
    fun providesCacheReposUseCase(cacheRepository: CacheRepository) : CacheReposUseCase{
        return CacheReposUseCase(cacheRepository)
    }

    @Singleton
    @Provides
    fun providesGetReposFromCacheUseCase(cacheRepository: CacheRepository) : GetReposFromCacheUseCase{
        return GetReposFromCacheUseCase(cacheRepository)
    }

    @Provides
    @Singleton
    fun providesSearchUserUseCase(remoteRepository: RemoteRepository) : GetSearchFromInternet{
        return GetSearchFromInternet(remoteRepository)
    }

    @Provides
    @Singleton
    fun providesGetResultFromCacheUserUseCase(cacheRepository: CacheRepository) : GetSearchFromCacheUseCase{
        return GetSearchFromCacheUseCase(cacheRepository)
    }

    @Provides
    @Singleton
    fun providesCacheSearchBaseUseCase(cacheRepository: CacheRepository) : CacheSearchUseCase{
        return CacheSearchUseCase(cacheRepository)
    }

    @Provides
    @Singleton
    fun providesGetFollowersFromInternetUseCase(remoteRepository: RemoteRepository) : GetFollowersFromInternetUseCase{
        return GetFollowersFromInternetUseCase(remoteRepository)
    }

    @Provides
    @Singleton
    fun providesCacheFollowersCaseBaseUseCase(cacheRepository: CacheRepository) : CacheFollowersUseCase{
        return CacheFollowersUseCase(cacheRepository)
    }

    @Provides
    @Singleton
    fun providesGetFollowersFromCacheUseCase(cacheRepository: CacheRepository) : GetFollowersFromCacheUseCase{
        return GetFollowersFromCacheUseCase(cacheRepository)
    }


}