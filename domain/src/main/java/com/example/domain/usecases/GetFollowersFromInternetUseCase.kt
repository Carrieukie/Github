package com.example.domain.usecases

import com.example.domain.models.UserAndFollow
import com.example.domain.repositories.RemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

typealias GetFollowersBaseUseCase = BaseUseCase<String, Flow<UserAndFollow>>

class GetFollowersFromInternetUseCase(
    private val remoteRepository: RemoteRepository
) : GetFollowersBaseUseCase {

    override suspend fun invoke(params: String): Flow<UserAndFollow> {
        val users = remoteRepository.getFollowers(params)
        val userAndFollow = UserAndFollow(params,users)
        return flow { emit(userAndFollow) }
    }

}