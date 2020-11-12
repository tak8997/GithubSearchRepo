package com.tak8997.github.githubsearchrepo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.tak8997.github.githubsearchrepo.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {

    private lateinit var viewModel: SearchViewModel
    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        binding = DataBindingUtil.setContentView<ActivitySearchBinding>(this, R.layout.activity_search)
                .apply {
                    viewModel = this@SearchActivity.viewModel
                }

        viewModel.navigateToUserRepo.observe(this) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("testapp://repos/$it")))
        }
    }
}