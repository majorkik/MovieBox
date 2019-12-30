package com.majorik.moviebox.extensions

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Outline
import android.os.Build
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.majorik.moviebox.GlideApp
import com.majorik.moviebox.R

fun ImageView.displayImageWithCenterInside(
    url: String?,
    placeholder: Int = android.R.color.darker_gray
) {
    if (url != null) {
        GlideApp.with(this)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(placeholder)
            .error(placeholder)
            .centerInside()
            .into(this)
    } else {
        setImageDrawable(ContextCompat.getDrawable(context, placeholder))
    }
}

fun ImageView.displayImageWithCenterCrop(
    url: String?
) {
    if (url != null) {
        GlideApp.with(this)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(ContextCompat.getDrawable(context, R.drawable.placeholder_triangle))
            .centerCrop()
            .into(this)
    } else {
        setImageDrawable(ContextCompat.getDrawable(context, R.drawable.placeholder_triangle))
    }
}

fun ImageView.setBlackAndWhite() {
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