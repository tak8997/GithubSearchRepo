package com.tak8997.github.githubsearchrepo

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("login") val name: String,
    @SerializedName("avatar_url") val imageUrl: String
)
