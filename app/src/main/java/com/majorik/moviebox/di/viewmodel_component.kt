package com.majorik.moviebox.di

import com.majorik.moviebox.ui.movie.MovieViewModel
import com.majorik.moviebox.ui.movieDetails.MovieDetailsViewModel
import com.majorik.moviebox.ui.tv.TVViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MovieViewModel(get()) }
    viewModel { TVViewModel(get()) }
    viewModel {MovieDetailsViewModel(get())}
}