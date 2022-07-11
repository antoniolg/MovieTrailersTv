package com.devexperto.movietrailerstv.ui.main

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.fragment.app.Fragment
import androidx.leanback.app.BackgroundManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

class BackgroundState(private val fragment: Fragment) {
    private val backgroundManager by lazy {
        val activity = fragment.requireActivity()
        BackgroundManager.getInstance(activity).apply {
            attach(activity.window)
        }
    }

    private val target = object : CustomTarget<Bitmap>() {
        override fun onResourceReady(
            bitmap: Bitmap,
            transition: Transition<in Bitmap>?
        ) {
            backgroundManager.setBitmap(bitmap)
        }

        override fun onLoadCleared(placeholder: Drawable?) {}
    }

    fun loadUrl(url: String) {
        Glide.with(fragment.requireContext())
            .asBitmap()
            .centerCrop()
            .load(url)
            .into<CustomTarget<Bitmap>>(target)
    }
}