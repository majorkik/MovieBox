package com.majorik.moviebox.extensions

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Outline
import android.os.Build
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.majorik.moviebox.GlideApp
import com.majorik.moviebox.R

fun ImageView.displayImageWithCenterInside(
    url: String?,
    placeholder: Int = R.drawable.placeholder_transparent
) {
    GlideApp.with(this)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .centerInside()
        .placeholder(placeholder)
        .error(placeholder)
        .fallback(placeholder)
        .into(this)
}

fun ImageView.displayImageWithCenterCrop(
    url: String?,
    placeholder: Int = R.drawable.placeholder_transparent
) {
    GlideApp.with(this)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .centerCrop()
        .placeholder(placeholder)
        .error(placeholder)
        .fallback(placeholder)
        .into(this)
}

fun ImageView.setGrayscaleTransformation() {
    val colorMatrix = ColorMatrix()
    colorMatrix.setSaturation(0F)
    val filter = ColorMatrixColorFilter(colorMatrix)
    this.colorFilter = filter
}

fun ImageView.setCorners(sizeDp: Int) {
    val curveRadius = sizeDp.toPx()

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        this.outlineProvider = object : ViewOutlineProvider() {
            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun getOutline(view: View?, outline: Outline?) {
                outline?.setRoundRect(
                    0,
                    0,
                    view!!.width,
                    (view.height + curveRadius),
                    curveRadius.toFloat()
                )
            }
        }

        this.clipToOutline = true
    }
}
