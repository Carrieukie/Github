package com.example.data_remote.repositories

import com.example.data_remote.api.GithubApi
import com.example.data_remote.mappers.toDomain
import com.example.data_remote.mappers.toDormain
import com.example.domain.models.Repository
import com.example.domain.models.SearchResult
import com.example.domain.models.User
import com.example.domain.repositories.RemoteRepository

class RemoteRepositoryImpl(private val githubApi: GithubApi) : RemoteRepository {

    override suspend fun searchUser(userName: String): SearchResult {
        return githubApi.searchUser(userName).toDomain()
    }

    override suspend fun getRepos(userName: String): List<Repository> {
        return githubApi.getRepositories(userName).map {
            it.toDormain()
        }
    }

    override suspend fun getFollowers(userName: String): List<User> {
        return githubApi.getFollowers(userName).map {
            it.toDomain()
        }
    }

    override suspend fun getFollowing(userName: String): List<User> {
       return githubApi.getFollowing(userName).map {
           it.toDomain()
       }
    }

}