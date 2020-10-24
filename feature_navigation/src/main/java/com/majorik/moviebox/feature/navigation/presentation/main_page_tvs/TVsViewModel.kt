package com.majorik.moviebox.feature.navigation.presentation.main_page_tvs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.majorik.library.base.constants.AppConfig
import com.majorik.moviebox.feature.navigation.data.repositories.TVRepository
import com.majorik.moviebox.feature.navigation.data.repositories.TrendingRepository
import com.majorik.moviebox.feature.navigation.data.repositories.YouTubeRepository
import com.majorik.moviebox.BuildConfig
import com.majorik.moviebox.domain.enums.collections.TVCollectionType
import com.majorik.moviebox.feature.navigation.domain.config.YouTubeConfig.YOUTUBE_PART_SNIPPET
import com.majorik.moviebox.feature.navigation.domain.config.YouTubeConfig.YOUTUBE_TVS_COUNT_RESULTS
import com.majorik.moviebox.feature.navigation.domain.config.YouTubeConfig.YOUTUBE_TVS_ORDER_BY_DATE
import com.majorik.moviebox.feature.navigation.domain.config.YouTubeConfig.YOUTUBE_TVS_TRAILERS_CHANNEL_ID
import com.majorik.moviebox.feature.navigation.presentation.main_page_tvs.datasources.TVCollectionsPagingDataSource
import com.majorik.moviebox.feature.navigation.presentation.main_page_tvs.datasources.TrendingTVsPagingDataSource

class TVsViewModel(
    private val tvRepository: TVRepository,
    private val trendingRepository: TrendingRepository,
    private val youTubeRepository: YouTubeRepository
) : ViewModel() {

    /**
     * Popular tvs
     */

    var popularTVsDataSource: TVCollectionsPagingDataSource? = null

    var popularTVsFlow = Pager(PagingConfig(pageSize = 20)) {
        TVCollectionsPagingDataSource(tvRepository, TVCollectionType.POPULAR).also {
            popularTVsDataSource = it
        }
    }.flow.cachedIn(viewModelScope)

    /**
     * Airing today tvs
     */

    var airingTodayTVsDataSource: TVCollectionsPagingDataSource? = null

    var airingTodayTVsFlow = Pager(PagingConfig(pageSize = 20)) {
        TVCollectionsPagingDataSource(tvRepository, TVCollectionType.AIRING_TODAY).also {
            airingTodayTVsDataSource = it
        }
    }.flow.cachedIn(viewModelScope)

    /**
     * On the air tvs
     */

    var onTheAirTVsDataSource: TVCollectionsPagingDataSource? = null

    var onTheAirTVsFlow = Pager(PagingConfig(pageSize = 20)) {
        TVCollectionsPagingDataSource(tvRepository, TVCollectionType.ON_THE_AIR).also {
            onTheAirTVsDataSource = it
        }
    }.flow.cachedIn(viewModelScope)

    /**
     * Trending tvs
     */

    var trendingTVsDataSource: TrendingTVsPagingDataSource? = null

    var trendingTVsFlow = Pager(PagingConfig(pageSize = 20)) {
        TrendingTVsPagingDataSource(trendingRepository, TrendingRepository.TimeWindow.WEEK, AppConfig.REGION).also {
            trendingTVsDataSource = it
        }
    }.flow.cachedIn(viewModelScope)

    val genresLiveData = liveData {
        emit(tvRepository.getTVGenres(AppConfig.REGION))
    }

    val trailersLiveData = liveData {
        val response = youTubeRepository.searchYouTubeVideos(
            BuildConfig.GRADLE_YOU_TUBE_KEY,
            YOUTUBE_PART_SNIPPET,
            YOUTUBE_TVS_COUNT_RESULTS,
            null,
            YOUTUBE_TVS_ORDER_BY_DATE,
            YOUTUBE_TVS_TRAILERS_CHANNEL_ID
        )

        emit(response)
    }
}
