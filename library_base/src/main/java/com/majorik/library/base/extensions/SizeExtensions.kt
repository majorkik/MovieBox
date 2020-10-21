package com.majorik.library.base.extensions

import android.content.res.Resources
import kotlin.math.roundToInt

fun Int.px() = (this * Resources.getSystem().displayMetrics.density).roundToInt()

fun Int.dp() = (this / Resources.getSystem().displayMetrics.density).roundToInt()

fun Int.getOrDefault(default: Int = 0) = if (this > 0) this else default
