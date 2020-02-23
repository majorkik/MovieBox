package com.majorik.moviebox.extensions

import com.soywiz.klock.DateFormat
import com.soywiz.klock.DateTimeTz
import com.soywiz.klock.KlockLocale
import com.soywiz.klock.locale.russian
import com.soywiz.klock.parse


fun String.toDate(inputFormat: String = "yyyy-MM-dd"): DateTimeTz {
    val dateFormat = DateFormat(inputFormat)
    return dateFormat.parse(this)
}

fun String.toYear(inputFormat: String = "yyyy-MM-dd", placeholder: String = ""): String {
    if (this.isNotBlank()) {
        val dateFormat = DateFormat(inputFormat)
        return dateFormat.parse(this).year.year.toString()
    } else {
        return placeholder
    }
}