package com.majorik.moviebox.feature.details.data

import com.majorik.moviebox.data.retrofit.createTmdbV3WebService
import com.majorik.moviebox.feature.details.data.api.DetailsRetrofitService
import com.majorik.moviebox.feature.details.data.repositories.AccountRepository
import com.majorik.moviebox.feature.details.data.repositories.MovieRepository
import com.majorik.moviebox.feature.details.data.repositories.PersonRepository
import com.majorik.moviebox.feature.details.data.repositories.TVRepository
import org.koin.dsl.module

val dataModule = module {
    single { createTmdbV3WebService<DetailsRetrofitService>() }

    factory { AccountRepository(get()) }
    factory { MovieRepository(get()) }
    factory { TVRepository(get()) }
    factory { PersonRepository(get()) }
}
