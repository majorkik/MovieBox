package com.majorik.moviebox

import android.app.Application
import android.content.Context
import android.os.Build
import androidx.multidex.MultiDex
import com.akexorcist.localizationactivity.core.LocalizationApplicationDelegate
import com.google.android.play.core.splitcompat.SplitCompat
import com.majorik.moviebox.feature.KoinManager
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber
import timber.log.Timber.DebugTree
import java.util.*

class MovieBoxApplication() : Application() {
    private var localizationDelegate = LocalizationApplicationDelegate()

    override fun onCreate() {
        super.onCreate()

        Logger.addLogAdapter(AndroidLogAdapter())
        Timber.plant(object : DebugTree() {
            override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                Logger.log(priority, tag, message, t)
            }
        })

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@MovieBoxApplication)
            modules(KoinManager.koinModules)
        }
    }

    override fun attachBaseContext(base: Context) {
        attachLocaleToBaseContext(base)

        super.attachBaseContext(base)

        SplitCompat.install(this)

        MultiDex.install(this)
    }

    private fun attachLocaleToBaseContext(base: Context) {
        val locale: Locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            base.resources.configuration.locales.get(0) ?: Locale.ENGLISH
        } else {
            base.resources.configuration.locale
        }

        localizationDelegate.setDefaultLanguage(base, locale)
    }
}
