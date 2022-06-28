package com.example.domain.models

data class User (
    var id: Int? = null,
    var reposUrl: String? = null,
    var followingUrl: String? = null,
    var login: String? = null,
    var starredUrl: String? = null,
    var followersUrl: String? = null,
    var type: String? = null,
    var url: String? = null,
    var avatarUrl: String? = null,
    var htmlUrl: String? = null,
)