package com.majorik.moviebox.di

import com.majorik.moviebox.utils.SharedPrefsManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val storageModule = module {
    single { SharedPrefsManager(androidContext()) }
}
