package com.devexperto.movietrailerstv.ui.main

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.fragment.app.Fragment
import androidx.leanback.app.BackgroundManager
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BackgroundState(private val fragment: Fragment) {
    private val backgroundManager by lazy {
        val activity = fragment.requireActivity()
        BackgroundManager.getInstance(activity).apply {
            attach(activity.window)
            isAutoReleaseOnStop = false
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

    private var job: Job? = null

    fun loadUrl(url: String) {
        job?.cancel()
        job = fragment.lifecycleScope.launch {
            delay(300)
            Glide.with(fragment.requireContext())
                .asBitmap()
                .centerCrop()
                .load(url)
                .into<CustomTarget<Bitmap>>(target)
        }
    }
}