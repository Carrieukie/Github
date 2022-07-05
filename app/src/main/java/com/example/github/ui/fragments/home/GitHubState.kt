package com.example.github.ui.fragments.home

import com.example.domain.models.Repository
import com.example.domain.models.SearchResult
import com.example.domain.models.UserAndFollow

data class GitHubState (
        val searchResult: SearchResult? = null,
        val repositories: List<Repository> ? = null,
        val userAndFollow: UserAndFollow ? = null,
        val isLoading : Boolean = false,
        val error: String? = null
)