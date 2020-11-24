package com.majorik.moviebox.feature.search.presentation

import com.majorik.moviebox.feature.search.presentation.ui.discover.DiscoverViewModel
import com.majorik.moviebox.feature.search.presentation.ui.filters.DiscoverFiltersViewModel
import com.majorik.moviebox.feature.search.presentation.ui.search.movie.SearchMovieViewModel
import com.majorik.moviebox.feature.search.presentation.ui.search.people.SearchPeopleViewModel
import com.majorik.moviebox.feature.search.presentation.ui.search.tv.SearchTVViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { SearchMovieViewModel(get()) }
    viewModel { SearchTVViewModel(get()) }
    viewModel { SearchPeopleViewModel(get()) }
    viewModel { DiscoverFiltersViewModel(get(), get()) }
    viewModel { DiscoverViewModel(get()) }
}
