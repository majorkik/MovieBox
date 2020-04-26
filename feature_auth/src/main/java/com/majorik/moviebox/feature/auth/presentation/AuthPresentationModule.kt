package com.majorik.moviebox.feature.auth.presentation

import com.majorik.library.base.storage.CredentialsPrefsManager
import com.majorik.moviebox.feature.auth.presentation.ui.first_start.AuthViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val authPresentationModule = module {
    factory { AuthViewModel(get()) }

    single { CredentialsPrefsManager(androidContext()) }
}
