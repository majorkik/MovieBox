package com.majorik.moviebox.feature.details.data

import com.majorik.moviebox.data.retrofit.createTmdbWebService
import org.koin.dsl.module

val detailsDataModule = module {
    single { createTmdbWebService<DetailsRetrofitService>()}
}
