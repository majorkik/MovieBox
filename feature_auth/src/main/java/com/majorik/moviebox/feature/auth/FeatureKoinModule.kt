package com.majorik.moviebox.feature.auth

import com.majorik.moviebox.feature.auth.data.authDataModule
import com.majorik.moviebox.feature.auth.domain.authDomainModule
import com.majorik.moviebox.feature.auth.presentation.authPresentationModule
import com.majorik.moviebox.feature.KoinModuleProvider
import org.koin.core.module.Module

object FeatureKoinModule : KoinModuleProvider {
    override val koinModule: List<Module>
        get() = listOf(authDataModule, authDomainModule, authPresentationModule)
}
