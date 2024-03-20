package com.example.pickleverse.presentation.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class GlideImageLoader {

    fun loadSimpleImage(url: String, placeToLoadImage: ImageView, shouldCacheImage: Boolean) {
        val diskCacheStrategy = if (shouldCacheImage) DiskCacheStrategy.ALL else DiskCacheStrategy.NONE

        Glide.with(placeToLoadImage)
            .load(url)
            .diskCacheStrategy(diskCacheStrategy)
            .into(placeToLoadImage)
    }
}