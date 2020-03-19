package com.majorik.domain.constants

import java.util.*

object AppConfig {
    var APP_LOCALE: Locale = Locale.getDefault()
    var REGION = "ru-RU" // default ru-RU

    val AVAILABLE_LANGUAGES: List<Locale> = listOf(
        Locale.ENGLISH,
        Locale("ru", "RU"),
        Locale("uk", "UA")
    )
}
