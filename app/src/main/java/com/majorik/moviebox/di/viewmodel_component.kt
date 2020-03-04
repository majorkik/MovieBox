package com.majorik.moviebox.di

import com.majorik.domain.enums.movie.MovieCollectionType
import com.majorik.domain.enums.movie.TVCollectionType
import com.majorik.moviebox.ui.first_start.AuthViewModel
import com.majorik.moviebox.ui.main_page_movies.MoviesViewModel
import com.majorik.moviebox.ui.main_page_profile.ProfileViewModel
import com.majorik.moviebox.ui.main_page_tvs.TVsViewModel
import com.majorik.moviebox.ui.movieDetails.MovieDetailsViewModel
import com.majorik.moviebox.ui.movieTabCollections.MovieCollectionsViewModel
import com.majorik.moviebox.ui.person_details.PersonDetailsViewModel
import com.majorik.moviebox.ui.search.movie.SearchMovieViewModel
import com.majorik.moviebox.ui.search.people.SearchPeopleViewModel
import com.majorik.moviebox.ui.search.tv.SearchTVViewModel
import com.majorik.moviebox.ui.seasonDetails.SeasonDetailsViewModel
import com.majorik.moviebox.ui.tvDetails.TVDetailsViewModel
import com.majorik.moviebox.ui.tvTabCollections.TVCollectionsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MoviesViewModel(get(), get(), get(), get()) }
    viewModel { TVsViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { MovieDetailsViewModel(get(), get()) }
    viewModel { TVDetailsViewModel(get(), get()) }
    viewModel { SearchMovieViewModel(get()) }
    viewModel { SearchTVViewModel(get()) }
    viewModel { SearchPeopleViewModel(get()) }
    viewModel { PersonDetailsViewModel(get()) }
    viewModel { SeasonDetailsViewModel(get()) }
    viewModel { AuthViewModel(get()) }
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
