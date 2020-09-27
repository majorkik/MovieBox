package com.majorik.moviebox.feature.search.data

import com.majorik.moviebox.data.retrofit.createTmdbV3WebService
import com.majorik.moviebox.feature.search.data.api.SearchApiService
import com.majorik.moviebox.feature.search.data.repositories.DiscoverRepository
import com.majorik.moviebox.feature.search.data.repositories.SearchRepository
import org.koin.dsl.module

val dataModule = module {
    single { createTmdbV3WebService<SearchApiService>() }

    factory { SearchRepository(get()) }
    factory { DiscoverRepository(get()) }
}
