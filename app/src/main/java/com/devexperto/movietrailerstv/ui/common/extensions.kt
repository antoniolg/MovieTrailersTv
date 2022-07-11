package com.devexperto.movietrailerstv.ui.common

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.leanback.widget.DetailsOverviewRow
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

fun ImageView.loadUrl(url: String) {
    Glide.with(this).load(url).into(this)
}

fun DetailsOverviewRow.loadImageUrl(context: Context, url: String) {
    Glide.with(context)
        .load(url)
        .centerCrop()
        .into<CustomTarget<Drawable>>(object : CustomTarget<Drawable>(176, 313) {
            override fun onResourceReady(
                drawable: Drawable,
                transition: Transition<in Drawable>?
            ) {
                imageDrawable = drawable
            }

            override fun onLoadCleared(placeholder: Drawable?) {}
        })
}