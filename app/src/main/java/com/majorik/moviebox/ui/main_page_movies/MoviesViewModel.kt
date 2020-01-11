package com.majorik.moviebox.ui.main_page_movies

import androidx.lifecycle.MutableLiveData
import com.majorik.data.repositories.MovieRepository
import com.majorik.domain.tmdbModels.movie.Movie
import com.majorik.moviebox.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val movieRepository: MovieRepository
) : BaseViewModel() {
    val popularMoviesLiveData = MutableLiveData<MutableList<Movie>>()
    val upcomingMoviesLiveData = MutableLiveData<MutableList<Movie>>()
    val nowPlayingMoviesLiveData = MutableLiveData<MutableList<Movie>>()

    fun fetchPopularMovies(
        language: String?,
        page: Int?,
        region: String?
    ) {
        ioScope.launch {
            val response = movieRepository.getPopularMovies(language, page, region)

            response?.let { popularMoviesLiveData.postValue(response) }
        }
    }

    fun fetchUpcomingMovies(
        language: String?,
        page: Int?,
        region: String?
    ) {
        ioScope.launch {
            val response = movieRepository.getUpcomingMovies(language, page, region)

            response?.let { upcomingMoviesLiveData.postValue(response) }
        }
    }

    fun fetchNowPlayingMovies(
        language: String?,
        page: Int?,
        region: String?
    ) {
        ioScope.launch {
            val response = movieRepository.getNowPlayingMovies(language, page, region)

            response?.let { nowPlayingMoviesLiveData.postValue(response) }
        }
    }
}
