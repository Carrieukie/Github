package com.example.data_remote.api

import com.example.data_remote.responses.RepoResponse
import com.example.data_remote.responses.UserResponse
import com.example.data_remote.responses.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {

    @GET("users/{username}")
    suspend fun searchUser(
        @Path("username") userName: String
    ): SearchResponse

    @GET("users/{username}/followers")
    suspend fun getFollowers(
        @Path("username") userName: String
    ): List<UserResponse>

    @GET("users/{username}/following")
    suspend fun getFollowing(
        @Path("username") userName: String
    ): List<UserResponse>

    @GET("users/{username}/repos")
    suspend fun getRepositories(
        @Path("username") userName: String
    ): List<RepoResponse>


}