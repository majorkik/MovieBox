package com.majorik.library.base.extensions

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Outline
import android.os.Build
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import coil.load
import com.majorik.base.R

fun ImageView.displayImageWithCenterInside(
    url: String?,
    placeholder: Int = R.drawable.placeholder_transparent
) {
    load(url) {
        placeholder(placeholder)
        error(placeholder)
    }
}

fun ImageView.displayImageWithCenterCrop(
    url: String?,
    placeholder: Int = R.drawable.placeholder_transparent
) {
    scaleType = ImageView.ScaleType.CENTER_CROP

    load(url) {
        placeholder(placeholder)
        error(placeholder)
    }
}

fun ImageView.setGrayscaleTransformation() {
    val colorMatrix = ColorMatrix()
    colorMatrix.setSaturation(0F)
    val filter = ColorMatrixColorFilter(colorMatrix)
    this.colorFilter = filter
}

fun ImageView.setCorners(sizeDp: Int) {
    val curveRadius = sizeDp.px()

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

fun ImageView.setIndicatorColor(voteAverage: Double) {
    val selectedColor: Int = when {
        voteAverage >= 8.0 -> {
            R.color.emerald
        }

        voteAverage > 6.0 && voteAverage < 8.0 -> {
            R.color.colorAccent
        }

        else -> {
            R.color.sunset_orange
        }
    }

    this.setColorFilter(
        ContextCompat.getColor(
            this.context,
            selectedColor
        )
    )
}
