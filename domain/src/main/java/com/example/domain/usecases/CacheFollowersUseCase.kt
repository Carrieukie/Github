package com.example.domain.usecases

import com.example.domain.models.UserAndFollow
import com.example.domain.repositories.CacheRepository

typealias CacheFollowersCaseBaseUseCase = BaseUseCase<UserAndFollow, Unit>

class CacheFollowersUseCase(
    private val cacheRepository: CacheRepository
) : CacheFollowersCaseBaseUseCase{
    override suspend fun invoke(params: UserAndFollow) {
        cacheRepository.insertUsers(params)
    }
}