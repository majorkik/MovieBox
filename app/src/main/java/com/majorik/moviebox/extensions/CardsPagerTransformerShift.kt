package com.majorik.moviebox.extensions

import android.annotation.SuppressLint
import android.os.Build
import android.view.View
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

class CardsPagerTransformerShift(
    private val baseElevation: Int,
    private val raisingElevation: Int,
    private val smallerScale: Float,
    private val startOffset: Float
) : ViewPager.PageTransformer {

    @SuppressLint("NewApi")
    override fun transformPage(page: View, position: Float) {
        val absPosition = Math.abs(position - startOffset)

        if (absPosition >= 1) {
            page.elevation = baseElevation.toFloat()
            page.scaleY = smallerScale
        } else {
            page.elevation = (((1 - absPosition) * raisingElevation + baseElevation))
            page.scaleY = ((smallerScale - 1) * absPosition + 1)
        }
    }

}