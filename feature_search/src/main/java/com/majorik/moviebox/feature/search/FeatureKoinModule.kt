package com.majorik.moviebox.feature.search

import com.majorik.moviebox.feature.KoinModuleProvider
import com.majorik.moviebox.feature.search.data.dataModule
import com.majorik.moviebox.feature.search.domain.domainModule
import com.majorik.moviebox.feature.search.presentation.presentationModule
import org.koin.core.module.Module

object FeatureKoinModule : KoinModuleProvider {
    override val koinModule: List<Module>
        get() = listOf(dataModule, domainModule, presentationModule)
}
