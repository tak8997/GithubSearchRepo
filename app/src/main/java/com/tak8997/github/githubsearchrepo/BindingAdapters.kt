package com.tak8997.github.githubsearchrepo

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("android:url")
fun ImageView.setImage(url: String?) {
    if (url.isNullOrEmpty()) {
        return
    }

    Glide.with(context)
        .load(url)
        .centerCrop()
        .into(this)
}