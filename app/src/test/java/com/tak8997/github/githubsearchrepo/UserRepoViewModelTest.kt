package com.tak8997.github.githubsearchrepo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock


internal class UserRepoViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private val repository: UserRepoRepository = mock()

    private val viewModel = UserRepoViewModel(repository)

    @Test
    fun onCreateTest_WhenValidName() = runBlocking {
        val result: Observer<Pair<User?, List<Repo>?>> = mock()
        viewModel.viewState.observeForever(result)
        val name = "jake wharton"
        whenever(repository.fetchUserRepos(name))
            .doReturn(
                Pair(
                    Result.success(User("jake wharton", "jake.jpg")),
                    Result.success(listOf(Repo("android", "coroutine is difficult", "5")))
                )
            )

        viewModel.onCreate(name)

        verify(result).onChanged(
            Pair(
                User("jake wharton", "jake.jpg"),
                listOf(Repo("coroutine", "coroutine is difficult", "5"))
            )
        )
    }

    @Test
    fun onCreateTest_WhenInValidName() = runBlocking {
        val result: Observer<Int> = mock()
        viewModel.errorToast.observeForever(result)
        val name = ""

        viewModel.onCreate(name)

        verify(result).onChanged(R.string.something_went_wrong)
    }
}