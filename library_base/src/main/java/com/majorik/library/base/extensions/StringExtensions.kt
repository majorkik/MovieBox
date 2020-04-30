package com.majorik.library.base.extensions

import android.content.Intent
import androidx.fragment.app.Fragment
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
