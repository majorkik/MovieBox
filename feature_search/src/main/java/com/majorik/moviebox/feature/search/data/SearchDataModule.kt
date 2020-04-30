package com.majorik.moviebox.feature.search.data

import com.majorik.moviebox.data.retrofit.createTmdbWebService
import com.majorik.moviebox.feature.search.data.api.SearchApiService
import com.majorik.moviebox.feature.search.data.repositories.SearchRepository
import org.koin.dsl.module

val dataModule = module {
    single { createTmdbWebService<SearchApiService>() }

    factory { SearchRepository(get()) }
}
