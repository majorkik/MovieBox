package com.majorik.moviebox.feature.navigation.presentation

import com.majorik.moviebox.feature.navigation.presentation.main_page_movies.MoviesViewModel
import com.majorik.moviebox.feature.navigation.presentation.main_page_profile.ProfileViewModel
import com.majorik.moviebox.feature.navigation.presentation.main_page_tvs.TVsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { MoviesViewModel(get(), get(), get(), get()) }
    viewModel { TVsViewModel(get(), get(), get()) }
    viewModel { ProfileViewModel(get(), get()) }
}
