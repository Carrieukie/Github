package com.example.android_remote.mappers

import com.example.android_remote.entity.RepoEntity
import com.example.android_remote.entity.SearchEntity
import com.example.android_remote.entity.UserEntity
import com.example.android_remote.relations.UserWithFollowersCrossRef
import com.example.domain.models.Repository
import com.example.domain.models.SearchResult
import com.example.domain.models.User

internal fun Repository.toEntity(): RepoEntity = RepoEntity(
    id = id,
    allowForking = allowForking,
    stargazersCount = stargazersCount,
    isTemplate = isTemplate,
    pushedAt = pushedAt,
    subscriptionUrl = subscriptionUrl,
    language = language,
    branchesUrl = branchesUrl,
    issueCommentUrl = issueCommentUrl,
    labelsUrl = labelsUrl,
    subscribersUrl = subscribersUrl,
    releasesUrl = releasesUrl,
    svnUrl = svnUrl,
    forks = forks,
    archiveUrl = archiveUrl,
    gitRefsUrl = gitRefsUrl,
    forksUrl = forksUrl,
    visibility = visibility,
    statusesUrl = statusesUrl,
    sshUrl = sshUrl,
    fullName = fullName,
    size = size,
    languagesUrl = languagesUrl,
    htmlUrl =  htmlUrl,
    collaboratorsUrl = collaboratorsUrl,
    cloneUrl = cloneUrl,
    name = name,
    pullsUrl = pullsUrl,
    defaultBranch = defaultBranch,
    hooksUrl = hooksUrl,
    treesUrl = treesUrl,
    tagsUrl = tagsUrl,
    jsonMemberPrivate = jsonMemberPrivate,
    contributorsUrl = contributorsUrl,
    hasDownloads = hasDownloads,
    notificationsUrl = notificationsUrl,
    openIssuesCount = openIssuesCount,
    description = description,
    createdAt = createdAt,
    watchers = watchers,
    keysUrl = keysUrl,
    deploymentsUrl = deploymentsUrl,
    hasProjects = hasProjects,
    archived = archived,
    hasWiki = hasWiki,
    updatedAt = updatedAt,
    commentsUrl = commentsUrl,
    stargazersUrl = stargazersUrl,
    disabled = disabled,
    gitUrl = gitUrl,
    hasPages = hasPages,
    owner = owner,
    commitsUrl = commitsUrl,
    compareUrl = compareUrl,
    gitCommitsUrl = gitCommitsUrl,
    blobsUrl = blobsUrl,
    gitTagsUrl = gitTagsUrl,
    mergesUrl = mergesUrl,
    downloadsUrl = downloadsUrl,
    hasIssues = hasIssues,
    url = url,
    contentsUrl = contentsUrl,
    mirrorUrl = mirrorUrl,
    milestonesUrl = milestonesUrl,
    teamsUrl = teamsUrl,
    fork = fork,
    issuesUrl = issuesUrl,
    eventsUrl = eventsUrl,
    issueEventsUrl = issueEventsUrl,
    assigneesUrl = assigneesUrl,
    openIssues = openIssues,
    watchersCount = watchersCount,
    nodeId = nodeId,
    homepage = homepage,
    forksCount = forksCount
)

internal fun SearchResult.toEntity() = SearchEntity(
    id = id,
    gistsUrl = gistsUrl,
    reposUrl = reposUrl,
    followingUrl = followingUrl,
    twitterUsername = twitterUsername,
    bio = bio,
    createdAt = createdAt,
    login = login,
    type = type,
    blog = blog,
    subscriptionsUrl = subscriptionsUrl,
    updatedAt = updatedAt,
    siteAdmin = siteAdmin,
    company = company,
    publicRepos = publicRepos,
    gravatarId = gravatarId,
    email = email,
    organizationsUrl = organizationsUrl,
    hireable = hireable,
    starredUrl = starredUrl,
    followersUrl = followersUrl,
    publicGists = publicGists,
    url = url,
    receivedEventsUrl = receivedEventsUrl,
    followers = followers,
    avatarUrl = avatarUrl,
    eventsUrl = eventsUrl,
    htmlUrl = htmlUrl,
    following = following,
    name = name,
    location = location,
    nodeId = nodeId
)

internal fun SearchResult.toUser() = UserEntity(
    id = id,
    reposUrl = reposUrl,
    followingUrl = followingUrl,
    login = login!!,
    type = type,
    starredUrl = starredUrl,
    followersUrl = followersUrl,
    url = url,
    avatarUrl = avatarUrl,
    htmlUrl = htmlUrl,
)

internal fun User.toEntity() = UserEntity(
    id = id,
    reposUrl = reposUrl,
    followingUrl =followingUrl,
    starredUrl = starredUrl,
    login = login!!,
    followersUrl = followersUrl,
    type = type,
    url = url,
    avatarUrl = avatarUrl,
    htmlUrl = htmlUrl
)

internal fun User.toCrossRef(userId : Int) : UserWithFollowersCrossRef = UserWithFollowersCrossRef(
    id = userId,
    login = login!!
)




