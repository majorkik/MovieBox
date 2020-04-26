package com.majorik.moviebox.feature.navigation.data

import com.majorik.moviebox.data.retrofit.createTmdbWebService
import com.majorik.moviebox.data.retrofit.createYoutubeWebService
import com.majorik.moviebox.feature.navigation.data.api.TmdbApiService
import com.majorik.moviebox.feature.navigation.data.api.YouTubeApiService
import com.majorik.moviebox.feature.navigation.data.repositories.*
import org.koin.dsl.module

val dataModule = module {
    single { createTmdbWebService<TmdbApiService>() }
    single { createYoutubeWebService<YouTubeApiService>() }

    factory { MovieRepository(get()) }
    factory { PersonRepository(get()) }
    factory { TrendingRepository(get()) }
    factory { TVRepository(get()) }
    factory { YouTubeRepository(get()) }

}
