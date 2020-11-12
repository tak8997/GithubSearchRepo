package com.tak8997.github.githubsearchrepo

import androidx.lifecycle.*
import kotlinx.coroutines.launch

internal class UserRepoViewModel(
    private val repository: UserRepoRepository = UserRepoRepository()
) : ViewModel() {

    private val userRepoResult = MutableLiveData<Pair<Result<User>, Result<List<Repo>>>>()
    val errorToast = MediatorLiveData<Int>().apply {
        addSource(userRepoResult) { (userResult, reposResult) ->
            if (userResult is Result.Error || reposResult is Result.Error) {
                value = R.string.something_went_wrong
            }
        }
    }
    val viewState: LiveData<Pair<User?, List<Repo>?>> =
        userRepoResult.map { (userResult, userReposResult) ->
            Pair(userResult.data, userReposResult.data)
        }

    fun onCreate(userName: String?) {
        if (userName.isNullOrEmpty()) {
            errorToast.value = R.string.something_went_wrong
            return
        }

        viewModelScope.launch {
            userRepoResult.value = repository.fetchUserRepos(userName)
        }
    }
}