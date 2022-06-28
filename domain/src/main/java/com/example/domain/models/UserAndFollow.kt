package com.example.domain.models

data class UserAndFollow (
    val user: String,
    val users : List<User>
    )