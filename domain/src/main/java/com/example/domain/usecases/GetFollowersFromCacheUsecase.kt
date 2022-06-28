package com.example.domain.usecases

import com.example.domain.models.UserAndFollow
import com.example.domain.repositories.CacheRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

typealias GetFollowersFromCacheBaseUseCase = BaseUseCase<String, Flow<UserAndFollow?>>


class GetFollowersFromCacheUseCase(
  private val cacheRepository: CacheRepository
) : GetFollowersFromCacheBaseUseCase {

    override suspend fun invoke(params: String): Flow<UserAndFollow?> {
        val userAndFollow = cacheRepository.getFollowersof(params)
        return flow { emit(userAndFollow) }
    }

}