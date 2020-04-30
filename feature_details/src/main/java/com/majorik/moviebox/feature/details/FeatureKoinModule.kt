package com.majorik.moviebox.feature.details

import com.majorik.moviebox.feature.KoinModuleProvider
import com.majorik.moviebox.feature.details.data.dataModule
import com.majorik.moviebox.feature.details.domain.domainModule
import com.majorik.moviebox.feature.details.presentation.presentationModule
import org.koin.core.module.Module

object FeatureKoinModule : KoinModuleProvider {
    override val koinModule: List<Module>
        get() = listOf(dataModule, domainModule, presentationModule)
}
