package com.majorik.moviebox.feature.navigation.presentation.main_page_tvs

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.majorik.moviebox.feature.navigation.data.repositories.TVRepository
import com.majorik.moviebox.feature.navigation.data.repositories.TrendingRepository
import com.majorik.moviebox.feature.navigation.data.repositories.YouTubeRepository
import com.majorik.moviebox.feature.navigation.domain.youtubeModels.SearchResponse
import com.majorik.library.base.models.results.ResultWrapper
import com.majorik.moviebox.BuildConfig
import com.majorik.moviebox.feature.navigation.domain.tmdbModels.genre.GenreResponse
import com.majorik.moviebox.feature.navigation.domain.tmdbModels.tv.TVResponse
import kotlinx.coroutines.launch

class TVsViewModel(
    private val tvRepository: TVRepository,
    private val trendingRepository: TrendingRepository,
    private val youTubeRepository: YouTubeRepository
) : ViewModel() {

    val popularTVsLiveData = MutableLiveData<ResultWrapper<TVResponse>>()
    val airTodayTVsLiveData = MutableLiveData<ResultWrapper<TVResponse>>()
    val trendingTVsLiveData = MutableLiveData<ResultWrapper<TVResponse>>()
    val onTheAirTVsLiveData = MutableLiveData<ResultWrapper<TVResponse>>()
    val genresLiveData = MutableLiveData<ResultWrapper<GenreResponse>>()
    val trailersLiveData = MutableLiveData<ResultWrapper<SearchResponse>>()

    fun fetchPopularTVs(language: String?, page: Int?) {
        viewModelScope.launch {
            val response = tvRepository.getPopularTVs(language, page)
            popularTVsLiveData.postValue(response)
        }
    }

    fun fetchAirTodayTVs(language: String?, page: Int?) {
        viewModelScope.launch {
            val response = tvRepository.getAiringTodayTVs(language, page)
            airTodayTVsLiveData.postValue(response)
        }
    }

    fun fetchOnTheAirTVs(language: String?, page: Int?) {
        viewModelScope.launch {
            val response = tvRepository.getOnTheAirTVs(language, page)
            onTheAirTVsLiveData.postValue(response)
        }
    }

    fun fetchTVGenres(language: String?) {
        viewModelScope.launch {
            val response = tvRepository.getTVGenres(language)
            genresLiveData.postValue(response)
        }
    }

    fun fetchTrendingTVs(
        timeWindow: TrendingRepository.TimeWindow,
        page: Int?,
        language: String?
    ) {
        viewModelScope.launch {
            val response = trendingRepository.getTrendingTVs(timeWindow, page, language)
            trendingTVsLiveData.postValue(response)
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
                "UCTxYD92kxevI1I1-oITiQzg"
            )
            trailersLiveData.postValue(response)
        }
    }
}
