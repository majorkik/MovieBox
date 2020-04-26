package com.majorik.moviebox.feature

import org.koin.core.module.Module

interface KoinModuleProvider {

    val koinModule: List<Module>
}
