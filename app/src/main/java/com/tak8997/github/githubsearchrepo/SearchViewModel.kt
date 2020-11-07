package com.tak8997.github.githubsearchrepo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

internal class SearchViewModel : ViewModel() {

    val navigateToUserRepo = MutableLiveData<Event<String>>()

    fun onClickUserRepo(name: String) {
        navigateToUserRepo.value = Event(name)
    }
}