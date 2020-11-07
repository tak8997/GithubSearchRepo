package com.tak8997.github.githubsearchrepo

import retrofit2.http.GET
import retrofit2.http.Path

interface GithubUserRepoApi {

    @GET("users/{userName}")
    suspend fun fetchUser(@Path("userName") userName: String) : User

    @GET("/users/{userName}/repos")
    suspend fun fetchRepos(@Path("userName") userName: String): List<Repo>
}