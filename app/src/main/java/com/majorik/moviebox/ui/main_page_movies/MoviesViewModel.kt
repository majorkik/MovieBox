package com.majorik.moviebox.ui.main_page_movies

import androidx.lifecycle.MutableLiveData
import com.majorik.data.repositories.MovieRepository
import com.majorik.data.repositories.PersonRepository
import com.majorik.data.repositories.TrendingRepository
import com.majorik.data.repositories.YouTubeRepository
import com.majorik.domain.tmdbModels.genre.Genre
import com.majorik.domain.tmdbModels.movie.Movie
import com.majorik.domain.tmdbModels.person.Person
import com.majorik.domain.youtubeModels.SearchResponse
import com.majorik.moviebox.BuildConfig
import com.majorik.moviebox.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val movieRepository: MovieRepository,
    private val personRepository: PersonRepository,
    private val trendingRepository: TrendingRepository,
    private val youTubeRepository: YouTubeRepository
) : BaseViewModel() {
    val popularMoviesLiveData = MutableLiveData<MutableList<Movie>>()
    val upcomingMoviesLiveData = MutableLiveData<MutableList<Movie>>()
    val nowPlayingMoviesLiveData = MutableLiveData<MutableList<Movie>>()
    val popularPeoplesLiveData = MutableLiveData<MutableList<Person>>()
    val genresLiveData = MutableLiveData<MutableList<Genre>>()
    val trendingMoviesLiveData = MutableLiveData<MutableList<Movie>>()
    val trailersLiveData = MutableLiveData<List<SearchResponse.Item>>()

    fun fetchPopularMovies(
        language: String?,
        page: Int?,
        region: String?
    ) {
        ioScope.launch {
            val response = movieRepository.getPopularMovies(language, page, region)

            response?.let { popularMoviesLiveData.postValue(it) }
        }
    }

    fun fetchUpcomingMovies(
        language: String?,
        page: Int?,
        region: String?
    ) {
        ioScope.launch {
            val response = movieRepository.getUpcomingMovies(language, page, region)

            response?.let { upcomingMoviesLiveData.postValue(it) }
        }
    }

    fun fetchNowPlayingMovies(
        language: String?,
        page: Int?,
        region: String?
    ) {
        ioScope.launch {
            val response = movieRepository.getNowPlayingMovies(language, page, region)

            response?.let { nowPlayingMoviesLiveData.postValue(it) }
        }
    }

    fun fetchMovieGenres(language: String?) {
        ioScope.launch {
            val response = movieRepository.getMovieGenres(language)

            response?.let { genresLiveData.postValue(it) }
        }
    }

    fun fetchPopularPeoples(language: String?, page: Int?) {
        ioScope.launch {
            val response = personRepository.getPopularPeoples(language, page)

            response?.let { popularPeoplesLiveData.postValue(it) }
        }
    }

    fun fetchTrendingMovies(
        timeWindow: TrendingRepository.TimeWindow,
        page: Int?,
        language: String?
    ) {
        ioScope.launch {
            val response = trendingRepository.getTrendingMovies(timeWindow, page, language)

            response?.let { trendingMoviesLiveData.postValue(it) }
        }
    }

    fun searchYouTubeVideosByChannel() {
        ioScope.launch {
            val response = youTubeRepository.searchYouTubeVideos(
                BuildConfig.YOUTUBE_API_KEY,
                "snippet",
                20,
                null,
                "date",
                "UCi8e0iOVk1fEOogdfu4YgfA"
            )

            response?.let { trailersLiveData.postValue(it.items) }
        }
    }
}
