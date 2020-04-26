package com.majorik.moviebox.feature.auth.data

import com.majorik.moviebox.feature.auth.data.repository.AuthTmdbRepository
import com.majorik.moviebox.feature.auth.data.retrofit.AuthRetrofitService
import com.majorik.moviebox.data.retrofit.createTmdbWebService
import org.koin.dsl.module

val authDataModule = module {
    single { createTmdbWebService<AuthRetrofitService>() }

    factory { AuthTmdbRepository(get()) }
}
