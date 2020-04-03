package com.majorik.feature.navigation.presentation.main_page_movies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.majorik.feature.navigation.data.repositories.MovieRepository
import com.majorik.feature.navigation.data.repositories.PersonRepository
import com.majorik.feature.navigation.data.repositories.TrendingRepository
import com.majorik.feature.navigation.data.repositories.YouTubeRepository
import com.majorik.feature.navigation.domain.tmdbModels.genre.Genre
import com.majorik.feature.navigation.domain.tmdbModels.movie.Movie
import com.majorik.feature.navigation.domain.tmdbModels.person.Person
import com.majorik.feature.navigation.domain.youtubeModels.SearchResponse
import com.majorik.library.base.models.results.ResultWrapper
import com.majorik.moviebox.BuildConfig
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val movieRepository: MovieRepository,
    private val personRepository: PersonRepository,
    private val trendingRepository: TrendingRepository,
    private val youTubeRepository: YouTubeRepository
) : ViewModel() {
    var popularMoviesLiveData = MutableLiveData<List<Movie>>()
    val upcomingMoviesLiveData = MutableLiveData<List<Movie>>()
    val nowPlayingMoviesLiveData = MutableLiveData<List<Movie>>()
    val popularPeoplesLiveData = MutableLiveData<List<Person>>()
    val genresLiveData = MutableLiveData<List<Genre>>()
    val trendingMoviesLiveData = MutableLiveData<List<Movie>>()
    val trailersLiveData = MutableLiveData<List<SearchResponse.Item>>()

    fun fetchPopularMovies(
        language: String?,
        page: Int?,
        region: String?
    ) {
        viewModelScope.launch {
            val response = movieRepository.getPopularMovies(language, page, region)

            when (response) {
                is ResultWrapper.NetworkError -> {
                }

                is ResultWrapper.GenericError -> {
                }

                is ResultWrapper.Success -> {
                    popularMoviesLiveData.postValue(response.value.results)
                }
            }
        }
    }

    fun fetchUpcomingMovies(
        language: String?,
        page: Int?,
        region: String?
    ) {
        viewModelScope.launch {
            val response = movieRepository.getUpcomingMovies(language, page, region)

            when (response) {
                is ResultWrapper.NetworkError -> {
                }

                is ResultWrapper.GenericError -> {
                }

                is ResultWrapper.Success -> {
                    upcomingMoviesLiveData.postValue(response.value.results)
                }
            }
        }
    }

    fun fetchNowPlayingMovies(
        language: String?,
        page: Int?,
        region: String?
    ) {
        viewModelScope.launch {
            val response = movieRepository.getNowPlayingMovies(language, page, region)

            when (response) {
                is ResultWrapper.NetworkError -> {
                }

                is ResultWrapper.GenericError -> {
                }

                is ResultWrapper.Success -> {
                    nowPlayingMoviesLiveData.postValue(response.value.results)
                }
            }
        }
    }

    fun fetchMovieGenres(language: String?) {
        viewModelScope.launch {
            val response = movieRepository.getMovieGenres(language)

            when (response) {
                is ResultWrapper.NetworkError -> {
                }

                is ResultWrapper.GenericError -> {
                }

                is ResultWrapper.Success -> {
                    genresLiveData.postValue(response.value.genres)
                }
            }
        }
    }

    fun fetchPopularPeoples(language: String?, page: Int?) {
        viewModelScope.launch {
            val response = personRepository.getPopularPeoples(language, page)

            when (response) {
                is ResultWrapper.NetworkError -> {
                }

                is ResultWrapper.GenericError -> {
                }

                is ResultWrapper.Success -> {
                    popularPeoplesLiveData.postValue(response.value.results)
                }
            }
        }
    }

    fun fetchTrendingMovies(
        timeWindow: TrendingRepository.TimeWindow,
        page: Int?,
        language: String?
    ) {
        viewModelScope.launch {
            val response = trendingRepository.getTrendingMovies(timeWindow, page, language)

            when (response) {
                is ResultWrapper.NetworkError -> {
                }

                is ResultWrapper.GenericError -> {
                }

                is ResultWrapper.Success -> {
                    trendingMoviesLiveData.postValue(response.value.results)
                }
            }
        }
    }

    fun searchYouTubeVideosByChannel() {
        viewModelScope.launch {
            val response = youTubeRepository.searchYouTubeVideos(
                BuildConfig.GRADLE_YOU_TUBE_KEY,
                "snippet",
                20,
                null,
                "date",
                "UCi8e0iOVk1fEOogdfu4YgfA"
            )

            when (response) {
                is ResultWrapper.NetworkError -> {
                }

                is ResultWrapper.GenericError -> {
                }

                is ResultWrapper.Success -> {
                    trailersLiveData.postValue(response.value.items)
                }
            }
        }
    }
}
