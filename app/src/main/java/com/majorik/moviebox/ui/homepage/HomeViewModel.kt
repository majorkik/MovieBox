package com.majorik.moviebox.ui.homepage

import androidx.lifecycle.MutableLiveData
import com.majorik.data.repositories.MovieRepository
import com.majorik.data.repositories.TVRepository
import com.majorik.domain.models.movie.MovieResponse
import com.majorik.domain.models.tv.TVResponse
import com.majorik.moviebox.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class HomeViewModel(
    private val movieRepository: MovieRepository,
    private val tvRepository: TVRepository
) : BaseViewModel() {
    val popularMoviesLiveData = MutableLiveData<MutableList<MovieResponse.Movie>>()
    val popularTVsLiveData = MutableLiveData<MutableList<TVResponse.TV>>()
    val upcomingMoviesLiveData = MutableLiveData<MutableList<MovieResponse.Movie>>()
    val airingTodayTVLiveData = MutableLiveData<MutableList<TVResponse.TV>>()

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

    fun fetchPopularTVs(
        language: String?,
        page: Int?
    ) {
        ioScope.launch {
            val popularTVs = tvRepository.getPopularTVs(language, page)
            popularTVsLiveData.postValue(popularTVs)
        }
    }

    fun fetchAiringTodayTVs(
        language: String?,
        page: Int?
    ) {
        ioScope.launch {
            val airingTodayTVs = tvRepository.getAiringTodayTVs(language, page)
            airingTodayTVLiveData.postValue(airingTodayTVs)
        }
    }
}