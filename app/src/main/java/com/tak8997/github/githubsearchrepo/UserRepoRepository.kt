package com.tak8997.github.githubsearchrepo

import kotlinx.coroutines.async
import kotlinx.coroutines.supervisorScope

internal class UserRepoRepository(
    private val userRepoApi: GithubUserRepoApi = makeGithubRepoApi()
) {

    suspend fun fetchUserRepos(userName: String) = supervisorScope {
        val user = async { userRepoApi.fetchUser(userName) }
        val userRepo = async { userRepoApi.fetchRepos(userName) }
        val userResult = safeApiCall { user.await() }
        val userRepoResult = safeApiCall { userRepo.await() }

        return@supervisorScope Pair(userResult, userRepoResult)
    }
}