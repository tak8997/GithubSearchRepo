package com.tak8997.github.githubsearchrepo

import androidx.lifecycle.*
import kotlinx.coroutines.launch

internal class UserRepoViewModel(
    private val repository: UserRepoRepository = UserRepoRepository()
) : ViewModel() {

    private val userRepoResult = MutableLiveData<Pair<Result<User>, Result<List<Repo>>>>()
    val errorToast = MediatorLiveData<Event<Unit>>().apply {
        addSource(userRepoResult) { (userResult, _) ->
            if (userResult is Result.Error) {
                value = Event(Unit)
            }
        }
    }
    val viewState: LiveData<Pair<User?, List<Repo>?>> =
        userRepoResult.map { (userResult, userReposResult) ->
            Pair(userResult.data, userReposResult.data)
        }

    fun onCreate(userName: String?) {
        if (userName.isNullOrEmpty()) {
            errorToast.value = Event(Unit)
            return
        }

        viewModelScope.launch {
            userRepoResult.value = repository.fetchUserRepos(userName)
        }
    }
}