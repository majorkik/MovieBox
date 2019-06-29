package com.majorik.moviebox.ui.movie

import androidx.lifecycle.MutableLiveData
import com.majorik.data.repositories.MovieRepository
import com.majorik.domain.models.movie.MovieResponse
import com.majorik.moviebox.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class MovieViewModel(private val movieRepository: MovieRepository) : BaseViewModel() {
    val popularMoviesLiveData = MutableLiveData<MutableList<MovieResponse.Movie>>()
    val topRatedMoviesLiveData = MutableLiveData<MutableList<MovieResponse.Movie>>()
    val nowPlayingMoviesLiveData = MutableLiveData<MutableList<MovieResponse.Movie>>()
    val upcomingMoviesLiveData = MutableLiveData<MutableList<MovieResponse.Movie>>()

    fun fetchPopularMovies(
        language: String?,
        page: Int?,
        region: String?
    ) {
        ioScope.launch {
            val popularMovies = movieRepository.getPopularMovies(language, page, region)
            popularMoviesLiveData.postValue(popularMovies)
        }
    }

    fun fetchTopRatedMovies(
        language: String?,
        page: Int?,
        region: String?
    ) {
        ioScope.launch {
            val topRatedMovies = movieRepository.getTopRatedMovies(language, page, region)
            topRatedMoviesLiveData.postValue(topRatedMovies)
        }
    }

    fun fetchNowPlayingMovies(
        language: String?,
        page: Int?,
        region: String?
    ) {
        ioScope.launch {
            val nowPlayingMovies = movieRepository.getNowPlayingMovies(language, page, region)
            nowPlayingMoviesLiveData.postValue(nowPlayingMovies)
        }
    }

    fun fetchUpcomingMovies(
        language: String?,
        page: Int?,
        region: String?
    ) {
        ioScope.launch {
            val upcomingMovies = movieRepository.getUpcomingMovies(language, page, region)
            upcomingMoviesLiveData.postValue(upcomingMovies)
        }
    }
}