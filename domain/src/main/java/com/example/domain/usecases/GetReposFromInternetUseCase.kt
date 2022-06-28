package com.example.domain.usecases

import com.example.domain.models.Repository
import com.example.domain.repositories.RemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

typealias GetReposBaseUseCase = BaseUseCase<String, Flow<List<Repository>>>

class GetReposFromInternetUseCase(
    private val remoteRepository: RemoteRepository
) : GetReposBaseUseCase {

    override suspend fun invoke(params: String): Flow<List<Repository>> {
        return flow { emit(remoteRepository.getRepos(params)) }
    }
}