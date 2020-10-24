package com.majorik.moviebox.feature.details.presentation

import com.majorik.moviebox.feature.details.presentation.movieDetails.MovieDetailsViewModel
import com.majorik.moviebox.feature.details.presentation.person_details.PersonDetailsViewModel
import com.majorik.moviebox.feature.details.presentation.seasonDetails.SeasonDetailsViewModel
import com.majorik.moviebox.feature.details.presentation.tvDetails.TVDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { MovieDetailsViewModel(get(), get(), get()) }
    viewModel { TVDetailsViewModel(get(), get(), get()) }
    viewModel { PersonDetailsViewModel(get()) }
    viewModel { SeasonDetailsViewModel(get()) }
}
