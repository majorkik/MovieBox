package com.majorik.moviebox.di

import com.majorik.domain.models.movie.MovieCollectionType
import com.majorik.domain.models.tv.TVCollectionType
import com.majorik.moviebox.ui.movie.MovieViewModel
import com.majorik.moviebox.ui.movieDetails.MovieDetailsViewModel
import com.majorik.moviebox.ui.movieTabCollections.MovieCollectionsViewModel
import com.majorik.moviebox.ui.person.PersonDetailsViewModel
import com.majorik.moviebox.ui.search.SearchableViewModel
import com.majorik.moviebox.ui.seasonDetails.SeasonDetailsViewModel
import com.majorik.moviebox.ui.tv.TVViewModel
import com.majorik.moviebox.ui.tvDetails.TVDetailsViewModel
import com.majorik.moviebox.ui.tvTabCollections.TVCollectionsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MovieViewModel(get()) }
    viewModel { TVViewModel(get()) }
    viewModel { MovieDetailsViewModel(get()) }
    viewModel { TVDetailsViewModel(get()) }
    viewModel { SearchableViewModel(get()) }
    viewModel { PersonDetailsViewModel(get()) }
    viewModel { SeasonDetailsViewModel(get()) }
    viewModel { (tvCollectionType: TVCollectionType) ->
        TVCollectionsViewModel(
            get(),
            tvCollectionType
        )
    }
    viewModel { (movieCollectionType: MovieCollectionType) ->
        MovieCollectionsViewModel(
            get(),
            movieCollectionType
        )
    }
}