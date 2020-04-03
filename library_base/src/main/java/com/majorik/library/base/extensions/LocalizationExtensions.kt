package com.majorik.library.base.extensions

import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.majorik.library.base.constants.AppConfig
import java.util.*

fun LocalizationActivity.updateLanguage(locale: Locale) {
    setLanguage(locale)
    AppConfig.REGION = locale.toLanguageTag()
    AppConfig.APP_LOCALE = locale
}
