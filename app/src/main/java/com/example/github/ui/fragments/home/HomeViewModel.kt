package com.example.github.ui.fragments.home

import androidx.lifecycle.*
import com.example.domain.usecases.*
import com.example.domain.utils.Resource
import com.example.domain.utils.networkBoundResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val searchUserBaseUseCase: GetSearchFromInternet,
    private val cacheSearchUseCase: CacheSearchUseCase,
    private val getSearchFromCacheUseCase: GetSearchFromCacheUseCase,

    private val getReposFromInternetUseCase: GetReposFromInternetUseCase,
    private val cacheReposUsecase : CacheReposUseCase,
    private val getCachedRepository : GetReposFromCacheUseCase,

    private val getFollowersFromInternetUseCase: GetFollowersFromInternetUseCase,
    private val cacheFollowersCaseBaseUseCase: CacheFollowersUseCase,
    private val getFollowersFromCacheBaseUseCase: GetFollowersFromCacheUseCase
): ViewModel() {

    private var _uiState = MutableLiveData<Resource<*>>(Resource.Empty(null))
    val uiState get()  = _uiState

    fun setState(homeIntents: HomeIntents){
        viewModelScope.launch {
            when(homeIntents){
                is HomeIntents.SearchUser -> {
                    getUser(homeIntents.params as String).collect{
                        _uiState.value = it
                    }
                }
                is HomeIntents.SearchUserRespositories -> {
                    getUserRepositories(homeIntents.params as String).collect{
                        _uiState.value = it
                    }
                }
                is HomeIntents.SearchFollowers -> {
                    getFollowers(homeIntents.params as String).collect{
                        _uiState.value = it
                    }
                }
            }
        }

    }

    private fun getUser(userName : String) = networkBoundResource(
        // pass in the logic to query data from the database
        query = {
            getSearchFromCacheUseCase(userName)
        },
        // pass in the logic to fetch data from the api
        fetch = {

            //This is to show a progress bar
            delay(2000)
            searchUserBaseUseCase(userName).first()
        },

        //pass in the logic to save the result to the local cache
        saveFetchResult = { result ->
            cacheSearchUseCase(result)
        },

        // pass in the logic to determine if the networking call should be made
        // default is true
        shouldFetch = {true}
    )

    private fun getUserRepositories(repoOwner : String) = networkBoundResource(
        // pass in the logic to query data from the database
        query = {
            getCachedRepository(repoOwner)
        },
        // pass in the logic to fetch data from the api
        fetch = {

            //This is to show a progress bar
            delay(2000)
            getReposFromInternetUseCase(repoOwner).first()
        },

        //pass in the logic to save the result to the local cache
        saveFetchResult = { result ->
            cacheReposUsecase(result)
        },

        // pass in the logic to determine if the networking call should be made
        // default is true
        shouldFetch = {true}
    )

    private fun getFollowers(login : String) = networkBoundResource(
        // pass in the logic to query data from the database
        query = {
            getFollowersFromCacheBaseUseCase(login)
        },
        // pass in the logic to fetch data from the api
        fetch = {

            //This is to show a progress bar
            delay(2000)
            getFollowersFromInternetUseCase(login).first()
        },

        //pass in the logic to save the result to the local cache
        saveFetchResult = { result ->
            cacheFollowersCaseBaseUseCase(result)
        },

        // pass in the logic to determine if the networking call should be made
        // default is true
        shouldFetch = {true}
    )

}

sealed class HomeIntents(
    val params: Any? = null
) {
    class SearchUser(params: String) : HomeIntents(params)
    class SearchUserRespositories(params: String) : HomeIntents(params)
    class SearchFollowers(params: String) : HomeIntents(params)

}