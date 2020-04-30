package com.majorik.moviebox.feature.collections.presentation

import com.majorik.moviebox.feature.collections.domain.movie.MovieCollectionType
import com.majorik.moviebox.feature.collections.domain.movie.TVCollectionType
import com.majorik.moviebox.feature.collections.presentation.genres.GenresViewModel
import com.majorik.moviebox.feature.collections.presentation.movieTabCollections.MovieCollectionsViewModel
import com.majorik.moviebox.feature.collections.presentation.tvTabCollections.TVCollectionsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { GenresViewModel(get(), get()) }
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
