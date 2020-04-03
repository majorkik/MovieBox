package com.majorik.moviebox.di


import com.majorik.moviebox.ui.first_start.AuthViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
//    viewModel { MoviesViewModel(get(), get(), get(), get()) }
//    viewModel { TVsViewModel(get(), get(), get()) }
//    viewModel { ProfileViewModel(get()) }
//    viewModel { MovieDetailsViewModel(get(), get()) }
//    viewModel { TVDetailsViewModel(get(), get()) }
//    viewModel { SearchMovieViewModel(get()) }
//    viewModel { SearchTVViewModel(get()) }
//    viewModel { SearchPeopleViewModel(get()) }
//    viewModel { PersonDetailsViewModel(get()) }
//    viewModel { SeasonDetailsViewModel(get()) }
    viewModel { AuthViewModel(get()) }
//    viewModel { GenresViewModel(get(), get()) }
//    viewModel { (tvCollectionType: TVCollectionType) ->
//        TVCollectionsViewModel(
//            get(),
//            tvCollectionType
//        )
//    }
//    viewModel { (movieCollectionType: MovieCollectionType) ->
//        MovieCollectionsViewModel(
//            get(),
//            movieCollectionType
//        )
//    }
}
