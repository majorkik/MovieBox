package com.majorik.moviebox.extensions

import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.majorik.domain.constants.AppConfig
import java.util.*

fun LocalizationActivity.updateLanguage(locale: Locale) {
    setLanguage(locale)
    AppConfig.REGION = locale.toLanguageTag()
    AppConfig.APP_LOCALE = locale
}