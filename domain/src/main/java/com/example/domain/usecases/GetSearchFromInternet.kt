package com.example.domain.usecases

import com.example.domain.models.SearchResult
import com.example.domain.repositories.RemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

typealias SearchUserBaseUsecase = BaseUseCase<String, Flow<SearchResult>>

class GetSearchFromInternet (
    private val remoteRepository: RemoteRepository
) : SearchUserBaseUsecase {

    override suspend fun invoke(params: String): Flow<SearchResult> {
        return flow { emit(remoteRepository.searchUser(params)) }
    }

}