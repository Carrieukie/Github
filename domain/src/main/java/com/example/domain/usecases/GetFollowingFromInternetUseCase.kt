package com.example.domain.usecases

import com.example.domain.models.User
import com.example.domain.repositories.RemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

typealias GetFollowingBaseUseCase = BaseUseCase<String, Flow<List<User>>>

class GetFollowingFromInternetUseCase(
    private val remoteRepository: RemoteRepository
) : GetFollowingBaseUseCase {

    override suspend fun invoke(params: String): Flow<List<User>> {
        return flow { emit(remoteRepository.getFollowing(params)) }
    }
}