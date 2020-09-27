package com.majorik.library.base.extensions

import android.content.Context
import android.content.Intent
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.majorik.base.R
import com.majorik.library.base.utils.FontSpan
import com.majorik.library.base.utils.PACKAGE_NAME
import com.majorik.library.base.utils.loadClassOrReturnNull
import com.orhanobut.logger.Logger
import com.soywiz.klock.DateFormat
import com.soywiz.klock.DateTimeTz
import com.soywiz.klock.parse

fun String.toDate(inputFormat: String = "yyyy-MM-dd"): DateTimeTz {
    val dateFormat = DateFormat(inputFormat)
    return dateFormat.parse(this)
}

fun String.toYear(inputFormat: String = "yyyy-MM-dd", placeholder: String = ""): String {
    return if (this.isNotBlank()) {
        val dateFormat = DateFormat(inputFormat)
        dateFormat.parse(this).year.year.toString()
    } else {
        placeholder
    }
}

/**
 * For loading intents
 */

private fun intentTo(className: String): Intent =
    Intent(Intent.ACTION_VIEW).setClassName(PACKAGE_NAME, className)

fun String.loadIntentOrReturnNull(): Intent? =
    try {
        Class.forName(this).run { intentTo(this@loadIntentOrReturnNull) }
    } catch (e: ClassNotFoundException) {
        Logger.e(e.message ?: "loadIntentOrReturnNull()")
        null
    }

/**
 * For loading fragments
 */

fun String.loadFragmentOrReturnNull(): Fragment? =
    try {
        this.loadClassOrReturnNull<Fragment>()?.newInstance()
    } catch (e: ClassNotFoundException) {
        Logger.e(e.message ?: "loadFragmentOrReturnNull()")
        null
    }

/**
 * Combine string with fonts
 */

fun Context.combineString(
    leftString: String,
    rightString: String,
    leftFont: Int = R.font.cc_montserrat_regular,
    rightFont: Int = R.font.cc_montserrat_semibold,
    nameLeftFont: String = "cc_montserrat_regular",
    nameRightFont: String = "cc_montserrat_semibold",
    leftColor: Int = R.color.mine_shaft_alpha_66,
    rightColor: Int = R.color.mine_shaft,
    delimiter: String = ":"
): SpannableString {
    val spannableString = SpannableString("$leftString$delimiter $rightString")

    spannableString.setSpan(
        FontSpan(nameLeftFont, ResourcesCompat.getFont(this, leftFont)),
        0,
        leftString.length + 1,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    spannableString.setSpan(
        ForegroundColorSpan(ContextCompat.getColor(this, leftColor)),
        0,
        leftString.length + 1,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    spannableString.setSpan(
        FontSpan(nameRightFont, ResourcesCompat.getFont(this, rightFont)),
        leftString.length + 1,
        spannableString.length,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    spannableString.setSpan(
        ForegroundColorSpan(ContextCompat.getColor(this, rightColor)),
        leftString.length + 1,
        spannableString.length,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    return spannableString
}
