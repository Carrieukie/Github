package com.example.android_remote.repositories

import com.example.android_remote.dao.RepoDao
import com.example.android_remote.dao.SearchDao
import com.example.android_remote.dao.UserDao
import com.example.android_remote.entity.UserEntity
import com.example.android_remote.mappers.toDomain
import com.example.android_remote.mappers.toEntity
import com.example.android_remote.mappers.toUser
import com.example.android_remote.relations.UserWithFollowersCrossRef
import com.example.domain.models.Repository
import com.example.domain.models.SearchResult
import com.example.domain.models.User
import com.example.domain.models.UserAndFollow
import com.example.domain.repositories.CacheRepository

class CacheRepositoryImpl(
    private val repoDao: RepoDao,
    private val searchDao: SearchDao,
    private val userDao: UserDao
) : CacheRepository {

    override suspend fun insertRepos(repository: List<Repository>) {
        repoDao.insertRepos(repository.map { it.toEntity() })
    }

    override suspend fun getReposByLogin(login: String): List<Repository> {
        return repoDao.getReposById(login).map { it.toDomain() }
    }

    override suspend fun cacheSearchResult(searchResult: SearchResult) {
        searchDao.insert(searchResult.toEntity())
        val user = searchResult.toUser()
        userDao.insertUser(mutableListOf<UserEntity>().apply { add(user) })
    }

    override suspend fun searchResult(username: String): SearchResult? {
        val result = searchDao.search(username)?.toDomain()
        return result
    }

    override suspend fun insertUsers(userAndFollow: UserAndFollow) {
        userDao.insertUser(userAndFollow.users.map { it.toEntity() })
        insertUserFollower(userAndFollow)
    }

    override suspend fun insertUserFollower(userAndFollow: UserAndFollow) {
        val userFollowers =  userAndFollow.users.map { UserWithFollowersCrossRef(userAndFollow.user,it.id!!) }
        userDao.insertUserFollowers(userFollowers)
    }

    override suspend fun getFollowersof(login: String): UserAndFollow {
        val users =  userDao.getFollowersof(login)
        return UserAndFollow(users.user.login, users.followers.map { it.toDomain() })
    }

    override suspend fun getUserById(userId: Int): User {
        return userDao.getUserById(userId).toDomain()
    }

}