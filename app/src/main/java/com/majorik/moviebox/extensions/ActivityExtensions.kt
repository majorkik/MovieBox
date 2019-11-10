package com.majorik.moviebox.extensions

import android.annotation.TargetApi
import android.app.Activity
import android.graphics.Color
import com.majorik.moviebox.utils.InsetUtil
import com.majorik.moviebox.utils.OnSystemInsetsChangedListener

@TargetApi(21)
fun Activity.setWindowTransparency(listener: OnSystemInsetsChangedListener = { _, _ -> }) {
    InsetUtil.removeSystemInsets(window.decorView, listener)
    window.statusBarColor = Color.TRANSPARENT
}