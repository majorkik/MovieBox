package com.majorik.library.base.extensions

import android.annotation.TargetApi
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.majorik.base.R
import com.majorik.library.base.constants.UrlConstants
import com.majorik.library.base.utils.FontSpan
import com.majorik.library.base.utils.InsetUtil
import com.majorik.library.base.utils.OnSystemInsetsChangedListener
import java.util.*

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

fun Context.startDetailsActivityWithId(
    id: Int,
    activity: String,
    animIn: Int = R.anim.slide_in_up,
    animOut: Int = R.anim.slide_out_up
) {
    val intent = activity.loadIntentOrReturnNull()

    intent?.putExtra("id", id)

    startActivity(intent)
    (this as? AppCompatActivity)?.overridePendingTransition(animIn, animOut)
}

fun Context.startActivityWithAnim(
    intent: Intent?,
    animIn: Int = R.anim.slide_in_up,
    animOut: Int = R.anim.slide_out_up
) {
    startActivity(intent)
    (this as? AppCompatActivity)?.overridePendingTransition(animIn, animOut)
}

fun Context.startActivityWithAnim(
    activity: String,
    animIn: Int = R.anim.slide_in_up,
    animOut: Int = R.anim.slide_out_up
) {
    val intent = activity.loadIntentOrReturnNull()
    startActivity(intent)
    (this as? AppCompatActivity)?.overridePendingTransition(animIn, animOut)
}

fun Context.startActivityWithAnim(
    activity: Class<*>,
    animIn: Int = R.anim.slide_in_up,
    animOut: Int = R.anim.slide_out_up
) {
    startActivity(Intent(this, activity))
    (this as? AppCompatActivity)?.overridePendingTransition(animIn, animOut)
}

fun AppCompatActivity.showToastMessage(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

@TargetApi(23)
fun Activity.setStatusBarModeForApi24(isDark: Boolean) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        var flags: Int = window.decorView.systemUiVisibility

        flags = if (isDark) {
            flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        } else {
            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        window.decorView.systemUiVisibility = flags
    }
}

fun Context.convertStringForFilmograohy(
    titleMovie: String,
    wordDelimiter: String,
    characterName: String
): SpannableStringBuilder {
    val fullText = "$titleMovie $wordDelimiter $characterName"
    SpannableStringBuilder(fullText).apply {
        setSpan(
            FontSpan(
                "cc_montserrat_regular",
                ResourcesCompat.getFont(
                    this@convertStringForFilmograohy,
                    R.font.cc_montserrat_regular
                )
            ),
            titleMovie.length,
            fullText.length - characterName.length,
            Spanned.SPAN_EXCLUSIVE_INCLUSIVE
        )

        return this
    }
}

fun Context.getCurrentLocale(): Locale {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        resources.configuration.locales.get(0) ?: Locale.ENGLISH
    } else {
        resources.configuration.locale
    }
}
