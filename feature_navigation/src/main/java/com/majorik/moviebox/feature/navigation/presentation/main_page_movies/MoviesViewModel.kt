package com.majorik.moviebox.feature.navigation.presentation.main_page_movies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.majorik.moviebox.feature.navigation.data.repositories.MovieRepository
import com.majorik.moviebox.feature.navigation.data.repositories.PersonRepository
import com.majorik.moviebox.feature.navigation.data.repositories.TrendingRepository
import com.majorik.moviebox.feature.navigation.data.repositories.YouTubeRepository
import com.majorik.moviebox.feature.navigation.domain.tmdbModels.genre.Genre
import com.majorik.moviebox.feature.navigation.domain.tmdbModels.movie.Movie
import com.majorik.moviebox.feature.navigation.domain.tmdbModels.person.Person
import com.majorik.moviebox.feature.navigation.domain.youtubeModels.SearchResponse
import com.majorik.library.base.models.results.ResultWrapper
import com.majorik.moviebox.BuildConfig
import com.majorik.moviebox.feature.navigation.domain.tmdbModels.genre.GenreResponse
import com.majorik.moviebox.feature.navigation.domain.tmdbModels.movie.MovieResponse
import com.majorik.moviebox.feature.navigation.domain.tmdbModels.person.PersonResponse
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val movieRepository: MovieRepository,
    private val personRepository: PersonRepository,
    private val trendingRepository: TrendingRepository,
    private val youTubeRepository: YouTubeRepository
) : ViewModel() {
    var popularMoviesLiveData = MutableLiveData<ResultWrapper<MovieResponse>>()
    val upcomingMoviesLiveData = MutableLiveData<ResultWrapper<MovieResponse>>()
    val nowPlayingMoviesLiveData = MutableLiveData<ResultWrapper<MovieResponse>>()
    val popularPeoplesLiveData = MutableLiveData<ResultWrapper<PersonResponse>>()
    val genresLiveData = MutableLiveData<ResultWrapper<GenreResponse>>()
    val trendingMoviesLiveData = MutableLiveData<ResultWrapper<MovieResponse>>()
    val trailersLiveData = MutableLiveData<ResultWrapper<SearchResponse>>()

    fun fetchPopularMovies(language: String?, page: Int?, region: String?) {
        viewModelScope.launch {
            val response = movieRepository.getPopularMovies(language, page, region)

            popularMoviesLiveData.postValue(response)
        }
    }

    fun fetchUpcomingMovies(language: String?, page: Int?, region: String?) {
        viewModelScope.launch {
            val response = movieRepository.getUpcomingMovies(language, page, region)

            upcomingMoviesLiveData.postValue(response)
        }
    }

    fun fetchNowPlayingMovies(language: String?, page: Int?, region: String?) {
        viewModelScope.launch {
            val response = movieRepository.getNowPlayingMovies(language, page, region)

            nowPlayingMoviesLiveData.postValue(response)
        }
    }

    fun fetchMovieGenres(language: String?) {
        viewModelScope.launch {
            val response = movieRepository.getMovieGenres(language)

            genresLiveData.postValue(response)
        }
    }

    fun fetchPopularPeoples(language: String?, page: Int?) {
        viewModelScope.launch {
            val response = personRepository.getPopularPeoples(language, page)

            popularPeoplesLiveData.postValue(response)
        }
    }

    fun fetchTrendingMovies(
        timeWindow: TrendingRepository.TimeWindow,
        page: Int?,
        language: String?
    ) {
        viewModelScope.launch {
            val response = trendingRepository.getTrendingMovies(timeWindow, page, language)

            trendingMoviesLiveData.postValue(response)
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

            trailersLiveData.postValue(response)
        }
    }
}
