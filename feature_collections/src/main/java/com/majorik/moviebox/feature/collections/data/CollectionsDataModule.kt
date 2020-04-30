package com.majorik.moviebox.feature.collections.data

import com.majorik.moviebox.data.retrofit.createTmdbWebService
import com.majorik.moviebox.feature.collections.data.api.CollectionApiService
import com.majorik.moviebox.feature.collections.data.repositories.*
import org.koin.dsl.module

val dataModule = module {
    single { createTmdbWebService<CollectionApiService>() }

    factory { MovieRepository(get()) }
    factory { TVRepository(get()) }
}
