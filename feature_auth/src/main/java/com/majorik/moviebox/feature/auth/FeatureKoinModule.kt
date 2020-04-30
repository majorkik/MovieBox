package com.majorik.moviebox.feature.auth

import com.majorik.moviebox.feature.auth.data.dataModule
import com.majorik.moviebox.feature.auth.domain.domainModule
import com.majorik.moviebox.feature.auth.presentation.presentationModule
import com.majorik.moviebox.feature.KoinModuleProvider
import org.koin.core.module.Module

object FeatureKoinModule : KoinModuleProvider {
    override val koinModule: List<Module>
        get() = listOf(dataModule, domainModule, presentationModule)
}
