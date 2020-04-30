package com.majorik.moviebox.feature.navigation.presentation.main_page_tvs

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.majorik.moviebox.feature.navigation.data.repositories.TVRepository
import com.majorik.moviebox.feature.navigation.data.repositories.TrendingRepository
import com.majorik.moviebox.feature.navigation.data.repositories.YouTubeRepository
import com.majorik.moviebox.feature.navigation.domain.tmdbModels.genre.Genre
import com.majorik.moviebox.feature.navigation.domain.tmdbModels.tv.TV
import com.majorik.moviebox.feature.navigation.domain.youtubeModels.SearchResponse
import com.majorik.library.base.models.results.ResultWrapper
import com.majorik.moviebox.BuildConfig
import com.orhanobut.logger.Logger
import kotlinx.coroutines.launch

class TVsViewModel(
    private val tvRepository: TVRepository,
    private val trendingRepository: TrendingRepository,
    private val youTubeRepository: YouTubeRepository
) : ViewModel() {

    val popularTVsLiveData = MutableLiveData<List<TV>>()
    val airTodayTVsLiveData = MutableLiveData<List<TV>>()
    val trendingTVsLiveData = MutableLiveData<List<TV>>()
    val onTheAirTVsLiveData = MutableLiveData<List<TV>>()
    val genresLiveData = MutableLiveData<List<Genre>>()
    val trailersLiveData = MutableLiveData<List<SearchResponse.Item>>()

    fun fetchPopularTVs(language: String?, page: Int?) {
        viewModelScope.launch {
            val response = tvRepository.getPopularTVs(language, page)

            when (response) {
                is ResultWrapper.NetworkError -> {
                }

                is ResultWrapper.GenericError -> {
                }

                is ResultWrapper.Success -> {
                    popularTVsLiveData.postValue(response.value.results)
                }
            }
        }
    }

    fun fetchAirTodayTVs(language: String?, page: Int?) {
        viewModelScope.launch {
            val response = tvRepository.getAiringTodayTVs(language, page)

            when (response) {
                is ResultWrapper.NetworkError -> {
                }

                is ResultWrapper.GenericError -> {
                }

                is ResultWrapper.Success -> {
                    airTodayTVsLiveData.postValue(response.value.results)
                }
            }
        }
    }

    fun fetchOnTheAirTVs(language: String?, page: Int?) {
        viewModelScope.launch {
            val response = tvRepository.getOnTheAirTVs(language, page)

            when (response) {
                is ResultWrapper.NetworkError -> {
                }

                is ResultWrapper.GenericError -> {
                }

                is ResultWrapper.Success -> {
                    onTheAirTVsLiveData.postValue(response.value.results)
                }
            }
        }
    }

    fun fetchTVGenres(language: String?) {
        viewModelScope.launch {
            val response = tvRepository.getTVGenres(language)

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

    fun fetchTrendingTVs(
        timeWindow: TrendingRepository.TimeWindow,
        page: Int?,
        language: String?
    ) {
        viewModelScope.launch {
            val response = trendingRepository.getTrendingTVs(timeWindow, page, language)

            when (response) {
                is ResultWrapper.NetworkError -> {
                }

                is ResultWrapper.GenericError -> {
                    Logger.d("GenericError - fetchTrendingTVs()")
                }

                is ResultWrapper.Success -> {
                    trendingTVsLiveData.postValue(response.value.results)
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
                "UCTxYD92kxevI1I1-oITiQzg"
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
