package com.example.android_remote.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.android_remote.entity.UserEntity

data class UserWithFollowers (
    @Embedded val user : UserEntity,
    @Relation (
        parentColumn = "login",
        entityColumn = "id",
        associateBy = Junction(
            value = UserWithFollowersCrossRef::class,
            parentColumn = "login",
            entityColumn = "id"
        )
    )
    val followers: List<UserEntity>
)