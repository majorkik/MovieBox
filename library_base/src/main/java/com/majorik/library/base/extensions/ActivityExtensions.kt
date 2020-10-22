package com.majorik.library.base.extensions

import android.annotation.TargetApi
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
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.majorik.base.R
import com.majorik.library.base.constants.UrlConstants
import com.majorik.library.base.utils.FontSpan
import com.majorik.library.base.utils.InsetUtil
import com.majorik.library.base.utils.OnSystemInsetsChangedListener
import java.util.*

@TargetApi(21)
fun AppCompatActivity.setWindowTransparency(listener: OnSystemInsetsChangedListener = { _, _ -> }) {
    InsetUtil.removeSystemInsets(window.decorView, listener)
    window.statusBarColor = Color.TRANSPARENT
}

fun FragmentActivity.setWindowTransparency(listener: OnSystemInsetsChangedListener = { _, _ -> }) {
    InsetUtil.removeSystemInsets(window.decorView, listener)
    window.statusBarColor = Color.TRANSPARENT
}

fun Fragment.setWindowTransparency(view: View, listener: OnSystemInsetsChangedListener = { _, _ -> }) {
    InsetUtil.removeSystemInsets(view, listener)
}

fun DialogFragment.setWindowTransparency(listener: OnSystemInsetsChangedListener = { _, _ -> }) {
    if (dialog?.window == null) return

    InsetUtil.removeSystemInsets(dialog?.window!!.decorView, listener)
    dialog?.window!!.statusBarColor = Color.TRANSPARENT
}

fun Context.startActivityWithAnim(
    activityLink: String,
    intent: Intent? = null,
    animIn: Int = R.anim.slide_in_up,
    animOut: Int = R.anim.slide_out_up
) {
    val activityIntent = activityLink.loadIntentOrReturnNull()

    intent?.let { activityIntent?.putExtras(it) }

    startActivity(activityIntent)
    (this as? AppCompatActivity)?.overridePendingTransition(animIn, animOut)
}

fun Context.startActivityWithAnim(
    activity: Class<*>,
    intent: Intent? = null,
    animIn: Int = R.anim.slide_in_up,
    animOut: Int = R.anim.slide_out_up
) {
    val activityIntent = Intent(this, activity)

    intent?.let { activityIntent.putExtras(it) }

    startActivity(activityIntent)
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

fun Context.showToastMessage(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

@TargetApi(23)
fun AppCompatActivity.setStatusBarModeForApi24(isDark: Boolean) {
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

fun Context.convertStringForFilmography(
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
                    this@convertStringForFilmography,
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

// YouTube intents
fun Context.openYouTube(key: String) {
    try {
        startActivity(getYouTubeAppIntent(key))
    } catch (ex: ActivityNotFoundException) {
        startActivity(getYouTubeWebIntent(key))
    }
}

private fun getYouTubeAppIntent(key: String) =
    Intent(Intent.ACTION_VIEW, Uri.parse(UrlConstants.YOUTUBE_VDN__LINK + key))

private fun getYouTubeWebIntent(key: String) = Intent(
    Intent.ACTION_VIEW, Uri.parse(UrlConstants.YOUTUBE_WEB__LINK + key)
)

fun AppCompatActivity.setDarkNavigationBarColor() {
    window.navigationBarColor = ContextCompat.getColor(this, R.color.mine_shaft)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}

fun FragmentActivity.setDarkNavigationBarColor() {
    window.navigationBarColor = ContextCompat.getColor(this, R.color.mine_shaft)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}
