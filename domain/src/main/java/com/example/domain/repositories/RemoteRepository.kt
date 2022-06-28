package com.example.domain.repositories

import com.example.domain.models.User
import com.example.domain.models.Repository
import com.example.domain.models.SearchResult

interface RemoteRepository {

    suspend fun searchUser(userName: String) : SearchResult

    suspend fun getRepos(userName: String) : List<Repository>

    suspend fun getFollowers(userName: String) : List<User>

    suspend fun getFollowing(userName: String) : List<User>

}