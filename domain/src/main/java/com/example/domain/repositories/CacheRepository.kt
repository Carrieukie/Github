package com.example.domain.repositories

import com.example.domain.models.Repository
import com.example.domain.models.SearchResult
import com.example.domain.models.User
import com.example.domain.models.UserAndFollow
import kotlinx.coroutines.flow.Flow

interface CacheRepository {

    suspend fun insertRepos(repository: List<Repository>)

    suspend fun getReposByLogin(login : String) : List<Repository>

    suspend fun cacheSearchResult(searchResult: SearchResult)

    suspend fun searchResult(username : String) : SearchResult?

    suspend fun insertUsers(userAndFollow: UserAndFollow)

    suspend fun insertUserFollower(userAndFollow: UserAndFollow)

    suspend fun getFollowersof(login: String): UserAndFollow

    suspend fun getUserById(userId : Int) : User

}
