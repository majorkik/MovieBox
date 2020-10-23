package com.majorik.moviebox.feature.navigation.presentation.main_page_movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.majorik.library.base.constants.AppConfig
import com.majorik.moviebox.BuildConfig
import com.majorik.moviebox.domain.enums.collections.MovieCollectionType
import com.majorik.moviebox.feature.navigation.data.repositories.MovieRepository
import com.majorik.moviebox.feature.navigation.data.repositories.PersonRepository
import com.majorik.moviebox.feature.navigation.data.repositories.TrendingRepository
import com.majorik.moviebox.feature.navigation.data.repositories.YouTubeRepository
import com.majorik.moviebox.feature.navigation.domain.config.YouTubeConfig.YOUTUBE_MOVIES_COUNT_RESULTS
import com.majorik.moviebox.feature.navigation.domain.config.YouTubeConfig.YOUTUBE_MOVIES_ORDER_BY_DATE
import com.majorik.moviebox.feature.navigation.domain.config.YouTubeConfig.YOUTUBE_MOVIES_TRAILERS_CHANNEL_ID
import com.majorik.moviebox.feature.navigation.domain.config.YouTubeConfig.YOUTUBE_PART_SNIPPET
import com.majorik.moviebox.feature.navigation.presentation.main_page_movies.datasources.MovieCollectionsPagingDataSource
import com.majorik.moviebox.feature.navigation.presentation.main_page_movies.datasources.TrendingMoviesPagingDataSource
import com.majorik.moviebox.feature.navigation.presentation.main_page_movies.datasources.TrendingPeoplesPagingDataSource

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
     * Peoples movies
     */

    var peoplesDataSource: TrendingPeoplesPagingDataSource? = null

    var peoplesFlow = Pager(PagingConfig(pageSize = 20)) {
        TrendingPeoplesPagingDataSource(personRepository, AppConfig.REGION).also {
            peoplesDataSource = it
        }
    }.flow.cachedIn(viewModelScope)

    /**
     * One-shot get genres list
     */

    val genresLiveData = liveData {
        emit(movieRepository.getMovieGenres(AppConfig.REGION))
    }

    /**
     * One-shot get movie trailers list
     */

    val trailersLiveData = liveData {
        val response = youTubeRepository.searchYouTubeVideos(
            BuildConfig.GRADLE_YOU_TUBE_KEY,
            YOUTUBE_PART_SNIPPET,
            YOUTUBE_MOVIES_COUNT_RESULTS,
            null,
            YOUTUBE_MOVIES_ORDER_BY_DATE,
            YOUTUBE_MOVIES_TRAILERS_CHANNEL_ID
        )

        emit(response)
    }
}
