package com.tak8997.github.githubsearchrepo

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.tak8997.github.githubsearchrepo.databinding.ActivityUserRepoBinding

internal class UserRepoActivity : AppCompatActivity() {

    private lateinit var viewModel: UserRepoViewModel
    private val userRepoAdapter by lazy { UserRepoAdapter() }
    private lateinit var binding: ActivityUserRepoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserRepoViewModel::class.java)
        binding = DataBindingUtil.setContentView<ActivityUserRepoBinding>(this, R.layout.activity_user_repo)
            .apply {
                viewModel = this@UserRepoActivity.viewModel
            }
        viewModel.onCreate(intent.data?.path?.substring(1))

        subscribe()
        setupRecycler()
    }

    private fun subscribe() {
        with(viewModel) {
            errorToast.observe(this@UserRepoActivity, EventObserver {
                Toast.makeText(this@UserRepoActivity, "", Toast.LENGTH_SHORT).show()
            })
            viewState.observe(this@UserRepoActivity, Observer {
                userRepoAdapter.setItems(it)
            })
        }
    }

    private fun setupRecycler() {
        binding.rvUser.adapter = userRepoAdapter
    }
}