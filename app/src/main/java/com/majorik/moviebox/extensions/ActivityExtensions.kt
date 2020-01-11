package com.majorik.moviebox.extensions

import android.annotation.TargetApi
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import com.majorik.domain.constants.UrlConstants
import com.majorik.moviebox.R
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

fun Context.startDetailsActivityWithId(
    id: Int,
    activity: Class<*>,
    animIn: Int = R.anim.slide_in_up,
    animOut: Int = R.anim.slide_out_up
) {
    val intent = Intent(this, activity)

    intent.putExtra("id", id)

    startActivity(intent)
    (this as? AppCompatActivity)?.overridePendingTransition(animIn, animOut)
}
