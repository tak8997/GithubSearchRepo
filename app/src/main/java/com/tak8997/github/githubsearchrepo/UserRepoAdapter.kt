package com.tak8997.github.githubsearchrepo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tak8997.github.githubsearchrepo.databinding.ItemUserBinding
import com.tak8997.github.githubsearchrepo.databinding.ItemUserRepoBinding

class UserRepoAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_USER = 0
        private const val TYPE_REPO = 1
    }

    private val repos = mutableListOf<Repo>()
    private var user: User? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            TYPE_USER -> {
                val binding = ItemUserBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return UserViewHolder(binding)
            }
            TYPE_REPO -> {
                val binding = ItemUserRepoBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return UserRepoViewHolder(binding)
            }
            else -> throw UnsupportedOperationException("invalid type")
        }
    }

    override fun getItemCount(): Int = repos.size + 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is UserViewHolder -> holder.bind(user)
            is UserRepoViewHolder -> holder.bind(repos[position - 1])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            TYPE_USER
        } else {
            TYPE_REPO
        }
    }

    fun setItems(repoResults: Pair<User, List<Repo>>?) {
        val user = repoResults?.first ?: return
        val repos = repoResults.second

        this.user = user
        this.repos.clear()
        this.repos.addAll(repos)
        notifyDataSetChanged()
    }

    private class UserViewHolder(
        private val binding: ItemUserBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User?) {
            binding.item = user
        }
    }

    private class UserRepoViewHolder(
        private val binding: ItemUserRepoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(repo: Repo) {
            binding.item = repo
        }
    }
}