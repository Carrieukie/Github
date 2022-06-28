package com.example.domain.usecases

import com.example.domain.models.SearchResult
import com.example.domain.repositories.CacheRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

typealias GetResultFromCacheBaseUseCase = BaseUseCase<String, Flow<SearchResult?>>

class GetSearchFromCacheUseCase(
    private val cacheRepository: CacheRepository
) : GetResultFromCacheBaseUseCase{

    override suspend fun invoke(params: String): Flow<SearchResult?> {
        val res = cacheRepository.searchResult(params)
        return flow { emit(res) }
    }

}