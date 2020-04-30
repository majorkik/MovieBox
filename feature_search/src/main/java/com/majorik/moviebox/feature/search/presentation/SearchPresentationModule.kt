package com.majorik.moviebox.feature.search.presentation

import com.majorik.moviebox.feature.search.presentation.ui.movie.SearchMovieViewModel
import com.majorik.moviebox.feature.search.presentation.ui.people.SearchPeopleViewModel
import com.majorik.moviebox.feature.search.presentation.ui.tv.SearchTVViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { SearchMovieViewModel(get()) }
    viewModel { SearchTVViewModel(get()) }
    viewModel { SearchPeopleViewModel(get()) }
}
