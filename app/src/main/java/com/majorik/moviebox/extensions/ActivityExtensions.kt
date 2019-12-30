package com.majorik.moviebox.extensions

import android.annotation.TargetApi
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import com.majorik.domain.constants.UrlConstants
import com.majorik.moviebox.utils.InsetUtil
import com.majorik.moviebox.utils.OnSystemInsetsChangedListener

@TargetApi(21)
fun Activity.setWindowTransparency(listener: OnSystemInsetsChangedListener = { _, _ -> }) {
    InsetUtil.removeSystemInsets(window.decorView, listener)
    window.statusBarColor = Color.TRANSPARENT
}

private fun getYouTubeAppIntent(key: String) =
    Intent(Intent.ACTION_VIEW, Uri.parse(UrlConstants.YOUTUBE_VDN__LINK + key))

private fun getYouTubeWebIntent(key: String) = Intent(
    Intent.ACTION_VIEW, Uri.parse(UrlConstants.YOUTUBE_WEB__LINK + key)
)

fun Context.openYouTube(key: String) {
    try {
        startActivity(getYouTubeAppIntent(key))
    } catch (ex: ActivityNotFoundException) {
        startActivity(getYouTubeWebIntent(key))
    }
}