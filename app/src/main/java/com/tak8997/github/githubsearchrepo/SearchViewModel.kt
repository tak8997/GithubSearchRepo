package com.tak8997.github.githubsearchrepo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

internal class SearchViewModel : ViewModel() {

    val navigateToUserRepo = MutableLiveData<String>()

    fun onClickUserRepo(name: String) {
        navigateToUserRepo.value = name
    }
}