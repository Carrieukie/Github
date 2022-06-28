package com.example.android_remote.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "searches")
data class SearchEntity(
	@PrimaryKey(autoGenerate = false)
	var id: Int? = null,
	var gistsUrl: String? = null,
	var reposUrl: String? = null,
	var followingUrl: String? = null,
	var twitterUsername: String? = null,
	var bio: String? = null,
	var createdAt: String? = null,
	var login: String? = null,
	var type: String? = null,
	var blog: String? = null,
	var subscriptionsUrl: String? = null,
	var updatedAt: String? = null,
	var siteAdmin: Boolean? = null,
	var company: String? = null,
	var publicRepos: Int? = null,
	var gravatarId: String? = null,
	var email: String? = null,
	var organizationsUrl: String? = null,
	var hireable: String? = null,
	var starredUrl: String? = null,
	var followersUrl: String? = null,
	var publicGists: Int? = null,
	var url: String? = null,
	var receivedEventsUrl: String? = null,
	var followers: Int? = null,
	var avatarUrl: String? = null,
	var eventsUrl: String? = null,
	var htmlUrl: String? = null,
	var following: Int? = null,
	var name: String? = null,
	var location: String? = null,
	var nodeId: String? = null
)

