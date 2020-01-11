package com.majorik.moviebox.utils

import android.view.View
import androidx.viewpager.widget.ViewPager
import kotlin.math.abs

class CardsPagerTransformerShift(
    private val baseElevation: Int,
    private val raisingElevation: Int,
    private val smallerScale: Float,
    private val startOffset: Float
) : ViewPager.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        val absPosition = abs(position - startOffset)

        if (absPosition >= 1) {
            page.elevation = baseElevation.toFloat()
            page.scaleY = smallerScale
        } else {
            page.elevation = (((1 - absPosition) * raisingElevation + baseElevation))
            page.scaleY = ((smallerScale - 1) * absPosition + 1)
        }
    }
}
