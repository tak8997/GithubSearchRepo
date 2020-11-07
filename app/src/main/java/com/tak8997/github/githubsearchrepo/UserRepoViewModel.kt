package com.tak8997.github.githubsearchrepo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

internal class UserRepoViewModel(
    private val repository: UserRepoRepository = UserRepoRepository()
) : ViewModel() {

    val errorToast = MutableLiveData<Event<Unit>>()
    val viewState = MutableLiveData<Pair<User, List<Repo>>>()

    fun onCreate(userName: String?) {
        if (userName.isNullOrEmpty()) {
            errorToast.value = Event(Unit)
            return
        }

        viewModelScope.launch {
            viewState.value = repository.fetchUserRepos(userName)
        }
    }
}