package com.majorik.moviebox.feature.auth.data

import com.majorik.moviebox.feature.auth.data.repository.AuthTmdbRepository
import com.majorik.moviebox.feature.auth.data.retrofit.AuthRetrofitService
import com.majorik.moviebox.data.retrofit.createTmdbV3WebService
import org.koin.dsl.module

val dataModule = module {
    single { createTmdbV3WebService<AuthRetrofitService>() }

    factory { AuthTmdbRepository(get()) }
}
