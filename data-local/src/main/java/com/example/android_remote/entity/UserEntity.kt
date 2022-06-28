package com.example.android_remote.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
	var id: Int? = null,
	var reposUrl: String? = null,
	var followingUrl: String? = null,
	var starredUrl: String? = null,
	@PrimaryKey(autoGenerate = false)
	var login: String,
	var followersUrl: String? = null,
	var type: String? = null,
	var url: String? = null,
	var avatarUrl: String? = null,
	var htmlUrl: String? = null,
)

