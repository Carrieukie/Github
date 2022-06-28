package com.example.domain.usecases

import com.example.domain.models.SearchResult
import com.example.domain.repositories.CacheRepository

typealias CacheSearchBaseUseCase = BaseUseCase<SearchResult, Unit>

class CacheSearchUseCase (
    private val cacheRepository: CacheRepository,
    ): CacheSearchBaseUseCase{
    override suspend fun invoke(params: SearchResult) {
        cacheRepository.cacheSearchResult(params)
    }
}