package com.majorik.moviebox.feature.navigation.data

import com.majorik.moviebox.data.retrofit.createTmdbV3WebService
import com.majorik.moviebox.data.retrofit.createTmdbV4WebService
import com.majorik.moviebox.data.retrofit.createYoutubeWebService
import com.majorik.moviebox.feature.navigation.data.api.TmdbApiService
import com.majorik.moviebox.feature.navigation.data.api.TmdbV4ApiService
import com.majorik.moviebox.feature.navigation.data.api.YouTubeApiService
import com.majorik.moviebox.feature.navigation.data.repositories.*
import org.koin.dsl.module

val dataModule = module {
    single { createTmdbV3WebService<TmdbApiService>() }
    single { createYoutubeWebService<YouTubeApiService>() }
    single { createTmdbV4WebService<TmdbV4ApiService>() }

    factory { MovieRepository(get()) }
    factory { PersonRepository(get()) }
    factory { TrendingRepository(get()) }
    factory { TVRepository(get()) }
    factory { YouTubeRepository(get()) }
    factory { AccountRepository(get()) }
}
