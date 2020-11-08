package com.tak8997.github.githubsearchrepo

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.lang.RuntimeException
import java.util.concurrent.TimeUnit


fun okhttp(): OkHttpClient {
    val loggingIntercepter = HttpLoggingInterceptor()
    loggingIntercepter.level = HttpLoggingInterceptor.Level.BODY

    return OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .addInterceptor(loggingIntercepter)
        .build()
}

fun retrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://api.github.com")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okhttp())
        .build()
}

fun makeGithubRepoApi(): GithubUserRepoApi {
    return retrofit().create(GithubUserRepoApi::class.java)
}

suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>): Result<T> {
    return safeApiResult(call)
}

private suspend fun <T : Any> safeApiResult(call: suspend () -> Response<T>): Result<T> {
    try {
        val response = call.invoke()
        if (!response.isSuccessful) {
            return Result.Error(NetworkException("http error"))
        }

        return if (response.body() == null) {
            Result.Error(NetworkException("data null"))
        } else {
            Result.Success(response.body()!!)
        }
    } catch (throwable: Throwable) {
        return when(throwable) {
            is IOException -> Result.Error(IOException())
            else -> Result.Error(NetworkException("unexpected error"))
        }
    }
}