package com.example.domain.usecases

import com.example.domain.models.Repository
import com.example.domain.repositories.CacheRepository

typealias CacheReposUseCaseBaseUseCase = BaseUseCase<List<Repository>, Unit>

class CacheReposUseCase(
    private val cacheRepository: CacheRepository
) : CacheReposUseCaseBaseUseCase{
    override suspend fun invoke(params: List<Repository>) {
        cacheRepository.insertRepos(params)
    }
}