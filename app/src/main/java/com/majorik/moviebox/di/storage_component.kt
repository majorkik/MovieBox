package com.majorik.moviebox.di

import com.majorik.library.base.storage.CredentialsPrefsManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val storageModule = module {
    single { CredentialsPrefsManager(androidContext()) }
}
