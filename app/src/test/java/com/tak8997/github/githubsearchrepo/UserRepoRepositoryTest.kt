package com.tak8997.github.githubsearchrepo

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

internal class UserRepoRepositoryTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val api: GithubUserRepoApi = mock()

    @Test
    fun fetchUserReposTest_Success() = runBlocking {
        val userName = "tak8997"
        whenever(api.fetchUser(userName)).thenReturn(
            Response.success(
                User(
                    "tak8997",
                    "tak8997.jpg"
                )
            )
        )
        whenever(api.fetchRepos(userName)).thenReturn(
            Response.success(
                listOf(
                    Repo(
                        "coroutine",
                        "coroutine is difficult",
                        "5"
                    )
                )
            )
        )

        val invocation = UserRepoRepository(api).fetchUserRepos(userName)

        assertThat(invocation).isEqualTo(
            Pair(
                Result.success(User("tak8997", "tak8997.jpg")),
                Result.success(listOf(Repo("coroutine", "coroutine is difficult", "5")))
            )
        )
    }

    @Test
    fun fetchUserRepoTest_Fail() = runBlocking {
        val userName = "tak8997"
        whenever(api.fetchUser(userName)).thenThrow(RuntimeException("unexpected error"))
        whenever(api.fetchRepos(userName)).thenReturn(
            Response.success(
                listOf(
                    Repo(
                        "coroutine",
                        "coroutine is difficult",
                        "5"
                    )
                )
            )
        )

        val invocation = UserRepoRepository(api).fetchUserRepos(userName)

        assertThat(invocation).isEqualTo(
            Pair(
                Result.Error(NetworkException("unexpected error")),
                Result.success(listOf(Repo("coroutine", "coroutine is difficult", "5")))
            )
        )
    }
}