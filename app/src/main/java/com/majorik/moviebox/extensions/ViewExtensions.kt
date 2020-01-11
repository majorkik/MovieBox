package com.majorik.moviebox.extensions

import android.view.View
import android.view.ViewGroup
import androidx.core.view.*

fun View.updateMargin(
    left: Int = marginLeft,
    top: Int = marginTop,
    right: Int = marginRight,
    bottom: Int = marginBottom
) = updateLayoutParams<ViewGroup.MarginLayoutParams> { updateMargins(left, top, right, bottom) }

fun View.setVisibilityOption(visibility: Boolean) = when {
    visibility -> this.visibility = View.VISIBLE
    else -> this.visibility = View.GONE
}
