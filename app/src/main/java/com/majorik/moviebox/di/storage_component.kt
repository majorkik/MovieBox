package com.majorik.moviebox.di

import com.majorik.moviebox.storage.CredentialsPrefsManager
import com.majorik.moviebox.storage.SharedPrefsManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val storageModule = module {
    single { SharedPrefsManager(androidContext()) }
    single { CredentialsPrefsManager(androidContext()) }
}
