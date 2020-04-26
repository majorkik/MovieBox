package com.majorik.moviebox.feature.navigation

import com.majorik.moviebox.feature.KoinModuleProvider
import com.majorik.moviebox.feature.navigation.data.dataModule
import com.majorik.moviebox.feature.navigation.presentation.presentationModule
import org.koin.core.module.Module

object FeatureKoinModule : KoinModuleProvider {
    override val koinModule: List<Module>
        get() = listOf(presentationModule, dataModule)
}
