package com.majorik.moviebox.feature.auth.presentation

import com.majorik.library.base.storage.CredentialsPrefsManager
import com.majorik.moviebox.feature.auth.presentation.ui.authorization.AuthorizationViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val presentationModule = module {
    factory { AuthorizationViewModel(get()) }

    single { CredentialsPrefsManager(androidContext()) }
}
