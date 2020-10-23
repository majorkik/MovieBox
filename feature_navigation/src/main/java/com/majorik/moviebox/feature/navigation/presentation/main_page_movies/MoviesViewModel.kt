package com.majorik.moviebox.feature.navigation.presentation.main_page_movies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.majorik.library.base.constants.AppConfig
import com.majorik.library.base.models.results.ResultWrapper
import com.majorik.moviebox.BuildConfig
import com.majorik.moviebox.domain.enums.collections.MovieCollectionType
import com.majorik.moviebox.feature.navigation.data.repositories.MovieRepository
import com.majorik.moviebox.feature.navigation.data.repositories.PersonRepository
import com.majorik.moviebox.feature.navigation.data.repositories.TrendingRepository
import com.majorik.moviebox.feature.navigation.data.repositories.YouTubeRepository
import com.majorik.moviebox.feature.navigation.domain.tmdbModels.person.PersonResponse
import com.majorik.moviebox.feature.navigation.domain.youtubeModels.SearchResponse
import com.majorik.moviebox.feature.navigation.presentation.main_page_movies.datasources.MovieCollectionsPagingDataSource
import com.majorik.moviebox.feature.navigation.presentation.main_page_movies.datasources.TrendingMoviesPagingDataSource
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val movieRepository: MovieRepository,
    private val personRepository: PersonRepository,
    private val trendingRepository: TrendingRepository,
    private val youTubeRepository: YouTubeRepository
) : ViewModel() {

    /**
     * Popular movies
     */

    var nowPlayingDataSource: MovieCollectionsPagingDataSource? = null

    var nowPlayingMoviesFlow = Pager(PagingConfig(pageSize = 20)) {
        MovieCollectionsPagingDataSource(movieRepository, MovieCollectionType.NOW_PLAYING).also {
            nowPlayingDataSource = it
        }
    }.flow.cachedIn(viewModelScope)

    /**
     * Upcoming movies
     */

    var upcomingDataSource: MovieCollectionsPagingDataSource? = null

    var upcomingMoviesFlow = Pager(PagingConfig(pageSize = 20)) {
        MovieCollectionsPagingDataSource(movieRepository, MovieCollectionType.UPCOMING).also {
            upcomingDataSource = it
        }
    }.flow.cachedIn(viewModelScope)


    /**
     * Upcoming movies
     */

    var trendingDataSource: TrendingMoviesPagingDataSource? = null

    var trendingMoviesFlow = Pager(PagingConfig(pageSize = 20)) {
        TrendingMoviesPagingDataSource(trendingRepository, TrendingRepository.TimeWindow.WEEK, AppConfig.REGION).also {
            trendingDataSource = it
        }
    }.flow.cachedIn(viewModelScope)


    /**
     * Upcoming movies
     */

    var popularDataSource: MovieCollectionsPagingDataSource? = null

    var popularMoviesFlow = Pager(PagingConfig(pageSize = 20)) {
        MovieCollectionsPagingDataSource(movieRepository, MovieCollectionType.POPULAR).also {
            popularDataSource = it
        }
    }.flow.cachedIn(viewModelScope)

    /**
     * One-shot get genres list
     */

    val genresLiveData = liveData {
        emit(movieRepository.getMovieGenres(AppConfig.REGION))
    }

    val popularPeoplesLiveData = MutableLiveData<ResultWrapper<PersonResponse>>()

    val trailersLiveData = MutableLiveData<ResultWrapper<SearchResponse>>()

    fun fetchPopularPeoples(language: String?, page: Int?) {
        viewModelScope.launch {
            val response = personRepository.getPopularPeoples(language, page)

            popularPeoplesLiveData.postValue(response)
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
