package com.tak8997.github.githubsearchrepo

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

internal class UserRepoRepository(
    private val userRepoApi: GithubUserRepoApi = makeGithubRepoApi()
) {

    suspend fun fetchUserRepos(userName: String) = coroutineScope {
        val user = async { userRepoApi.fetchUser(userName) }
        val userRepo = async { userRepoApi.fetchRepos(userName) }
        val userResult = user.await()
        val userRepoResult = userRepo.await()
        
        return@coroutineScope Pair(userResult, userRepoResult)
    }
}