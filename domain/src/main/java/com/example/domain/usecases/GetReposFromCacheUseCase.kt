package com.example.domain.usecases

import com.example.domain.models.Repository
import com.example.domain.repositories.CacheRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

typealias GetReposFromCacheBaseUseCase = BaseUseCase<String, Flow<List<Repository>>>

class GetReposFromCacheUseCase(
    private val cacheRepository: CacheRepository
) : GetReposFromCacheBaseUseCase{

    override suspend fun invoke(params: String): Flow<List<Repository>> {
        return flow { emit(cacheRepository.getReposByLogin(params)) }
    }

}