package com.majorik.moviebox.extensions

import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.majorik.moviebox.GlideApp


fun ImageView.displayImageWithCenterInside(url: String?, placeholder: Int = android.R.color.darker_gray) {
    if (url != null) {
        GlideApp.with(this)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(placeholder)
            .centerInside()
            .into(this)
    } else {
        setImageDrawable(ContextCompat.getDrawable(context, placeholder))
    }
}

fun ImageView.displayImageWithCenterCrop(url: String?, placeholder: Int = android.R.color.darker_gray) {
    if (url != null) {
        GlideApp.with(this)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(placeholder)
            .centerCrop()
            .into(this)
    } else {
        setImageDrawable(ContextCompat.getDrawable(context, placeholder))
    }
}