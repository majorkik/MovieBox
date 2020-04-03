package com.majorik.moviebox.feature

import com.majorik.moviebox.BuildConfig
import org.koin.core.module.Module

object KoinManager {

    private const val featurePackagePrefix = "com.majorik.moviebox.feature"

//    val koinModules:List<Module> = BuildConfig.FEATURE_MODULE_NAMES
//        .map { "$featurePackagePrefix.$it.FeatureKoinModule" }
//        .map {
//            try {
//                Class.forName(it).kotlin.objectInstance as KoinModuleProvider
//            } catch (e: ClassNotFoundException) {
//                throw ClassNotFoundException("Kodein module class not found $it")
//            }
//        }
//        .map { it.koinModule }
}
