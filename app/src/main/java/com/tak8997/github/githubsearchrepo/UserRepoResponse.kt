package com.tak8997.github.githubsearchrepo

import com.google.gson.annotations.SerializedName

data class Repo(
    val name: String?,
    val description: String?,
    @SerializedName("stargazers_count") val starCount: String?
)

data class User(
    @SerializedName("login") val name: String,
    @SerializedName("avatar_url") val imageUrl: String
)