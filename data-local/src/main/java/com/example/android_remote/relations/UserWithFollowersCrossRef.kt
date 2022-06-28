package com.example.android_remote.relations

import androidx.room.Entity

@Entity(primaryKeys = ["login","id"])
data class UserWithFollowersCrossRef(
    val login: String,
    val id: Int
)