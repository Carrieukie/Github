package com.example.github.ui.fragments.home

import androidx.lifecycle.ViewModel
import com.example.domain.usecases.*
import com.example.domain.utils.Resource
import com.example.domain.utils.networkBoundResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import timber.log.Timber
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

    private var _uiState = MutableStateFlow(GitHubState())
    val uiState get()  = _uiState

    suspend fun setState(homeIntents: HomeIntents){

        Timber.tag(TAG).i("Set State is : $homeIntents and search value is ${homeIntents.params}")

            when(homeIntents){
                is HomeIntents.SearchUser -> {

                    Timber.tag(TAG).i("Making a github search")

                    getUser(homeIntents.params as String).collect{ search ->
                        when(search){
                            is Resource.Error -> {

                                Timber.tag(TAG).e("Error occurred when seaching for a user ${search.error?.localizedMessage}")

                                uiState.value = uiState.value.copy(
                                    error = search.error?.localizedMessage,
                                    searchResult = search.data,
                                    isLoading = false
                                )
                            }

                            is Resource.Loading -> {

                                uiState.value = uiState.value.copy(
                                    isLoading = true,
                                    repositories = null,
                                    userAndFollow = null,
                                    error = null
                                )
                            }
                            is Resource.Success -> {

                                Timber.tag(TAG).i("Search success value is : ${search.data}")

                                uiState.value = uiState.value.copy(
                                    isLoading = false,
                                    searchResult = search.data
                                )
                            }
                            else -> {
                                uiState.value = uiState.value.copy(
                                    isLoading = false,
                                    error = "Something Unexpected happened"
                                )
                            }
                        }
                    }
                }
                is HomeIntents.SearchUserRespositories -> {
                    getUserRepositories(homeIntents.params as String).collect{ repositories ->
                        when(repositories){
                            is Resource.Error -> {

                                Timber.tag(TAG).e("Error occurred when searching for a repositories ${repositories.error?.localizedMessage}")

                                uiState.value = uiState.value.copy(
                                    error = repositories.error?.localizedMessage,
                                    repositories = repositories.data,
                                    isLoading = false

                                )
                            }
                            is Resource.Loading -> {
                                uiState.value = uiState.value.copy(
                                    isLoading = true
                                )
                            }
                            is Resource.Success -> {

                                Timber.tag(TAG).i("Search success value is : ${repositories.data}")

                                uiState.value = uiState.value.copy(
                                    isLoading = false,
                                    repositories = repositories.data
                                )
                            }
                        }
                    }
                }
                is HomeIntents.SearchFollowers -> {
                    getFollowers(homeIntents.params as String).collect{ followers ->
                        when(followers){
                            is Resource.Error -> {

                                Timber.tag(TAG).e("Error occurred when seaching for a followers ${followers.error}")


                                uiState.value = uiState.value.copy(
                                    error = followers.error?.localizedMessage,
                                    userAndFollow = followers.data,
                                    isLoading = false

                                )
                            }
                            is Resource.Loading -> {
                                uiState.value = uiState.value.copy(
                                    isLoading = true
                                )
                            }
                            is Resource.Success -> {
                                Timber.tag(TAG).i("Search success value is : ${followers.data}")

                                uiState.value = uiState.value.copy(
                                    isLoading = false,
                                    userAndFollow = followers.data
                                )
                            }
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

    companion object {
        private const val TAG = "HomeViewModel"
    }

}

sealed class HomeIntents(
    val params: Any? = null
) {
    class SearchUser(params: String) : HomeIntents(params)
    class SearchUserRespositories(params: String) : HomeIntents(params)
    class SearchFollowers(params: String) : HomeIntents(params)

}