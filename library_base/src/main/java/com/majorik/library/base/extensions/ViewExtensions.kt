package com.majorik.library.base.extensions

import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import androidx.core.view.updateLayoutParams
import androidx.core.view.updateMargins
import com.majorik.library.base.utils.DebounceSafeClickListener

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

fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
    val safeClickListener = DebounceSafeClickListener {
        onSafeClick(it)
    }
    setOnClickListener(safeClickListener)
}
