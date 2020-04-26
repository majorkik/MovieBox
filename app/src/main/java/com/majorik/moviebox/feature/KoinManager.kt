package com.majorik.moviebox.feature

import com.majorik.moviebox.BuildConfig
import org.koin.core.module.Module

object KoinManager {

    private const val featurePackagePrefix = "com.majorik.moviebox.feature"

    val koinModules: List<Module> = BuildConfig.FEATURE_MODULE_NAMES
        .map { "${featurePackagePrefix}.$it.FeatureKoinModule" }
        .map {
            try {
                Class.forName(it).kotlin.objectInstance as KoinModuleProvider
            } catch (e: ClassNotFoundException) {
                throw ClassNotFoundException("Koin module class not found $it,\n${e.exception}")
            }
        }
        .flatMap { it.koinModule }
}
