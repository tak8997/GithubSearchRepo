package com.tak8997.github.githubsearchrepo

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubUserRepoApi {

    @GET("users/{userName}")
    suspend fun fetchUser(@Path("userName") userName: String) : Response<User>

    @GET("/users/{userName}/repos")
    suspend fun fetchRepos(@Path("userName") userName: String): Response<List<Repo>>
}