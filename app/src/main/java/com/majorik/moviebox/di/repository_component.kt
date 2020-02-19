package com.majorik.moviebox.di

import com.majorik.data.repositories.*
import org.koin.dsl.module

val repositoryModule = module {
    factory { AccountRepository(get()) }
    factory { TmdbAuthRepository(get()) }
    factory { MovieRepository(get()) }
    factory { TVRepository(get()) }
    factory { PersonRepository(get()) }
    factory { SearchRepository(get()) }
    factory { TraktAuthRepository(get()) }
    factory { TraktSyncRepository(get()) }
    factory { TrendingRepository(get()) }
    factory { YouTubeRepository(get()) }
}
