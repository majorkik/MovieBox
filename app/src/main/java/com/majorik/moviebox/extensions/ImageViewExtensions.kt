package com.majorik.moviebox.extensions

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Outline
import android.os.Build
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.ImageView
import androidx.annotation.RequiresApi
import coil.api.load
import com.majorik.moviebox.R

fun ImageView.displayImageWithCenterInside(
    url: String?,
    placeholder: Int = R.drawable.placeholder_transparent
) {
    load(url) {
        placeholder(placeholder)
    }
}

fun ImageView.displayImageWithCenterCrop(
    url: String?,
    placeholder: Int = R.drawable.placeholder_transparent
) {
    scaleType = ImageView.ScaleType.CENTER_CROP

    load(url) {
        placeholder(placeholder)
    }
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
