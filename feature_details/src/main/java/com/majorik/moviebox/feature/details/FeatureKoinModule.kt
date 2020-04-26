package com.majorik.moviebox.feature.details

import com.majorik.moviebox.feature.KoinModuleProvider
import org.koin.core.module.Module

object FeatureKoinModule : KoinModuleProvider {
    override val koinModule: List<Module>
        get() = listOf()
}
