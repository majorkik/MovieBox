package com.majorik.moviebox.feature.collections

import com.majorik.moviebox.feature.KoinModuleProvider
import com.majorik.moviebox.feature.collections.data.dataModule
import com.majorik.moviebox.feature.collections.domain.domainModule
import com.majorik.moviebox.feature.collections.presentation.presentationModule
import org.koin.core.module.Module

object FeatureKoinModule : KoinModuleProvider {
    override val koinModule: List<Module>
        get() = listOf(dataModule, domainModule, presentationModule)
}
