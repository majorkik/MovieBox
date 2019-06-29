package com.majorik.moviebox.ui.tv

import androidx.lifecycle.MutableLiveData
import com.majorik.data.repositories.TVRepository
import com.majorik.domain.models.tv.TVResponse
import com.majorik.moviebox.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class TVViewModel(private val tvRepository: TVRepository) : BaseViewModel() {
    val popularTVsLiveData = MutableLiveData<MutableList<TVResponse.TV>>()
    val topRatedTVsLiveData = MutableLiveData<MutableList<TVResponse.TV>>()
    val airingTodayTVsLiveData = MutableLiveData<MutableList<TVResponse.TV>>()
    val onTheAirTVsLiveData = MutableLiveData<MutableList<TVResponse.TV>>()

    fun fetchPopularTVs(
        language: String?,
        page: Int?
    ) {
        ioScope.launch {
            val popularMovies = tvRepository.getPopularTVs(language, page)
            popularTVsLiveData.postValue(popularMovies)
        }
    }

    fun fetchTopRatedTVs(
        language: String?,
        page: Int?
    ) {
        ioScope.launch {
            val topRatedMovies = tvRepository.getTopRatedTVs(language, page)
            topRatedTVsLiveData.postValue(topRatedMovies)
        }
    }

    fun fetchAiringTodayTVs(
        language: String?,
        page: Int?
    ) {
        ioScope.launch {
            val airingTodayTVs = tvRepository.getAiringTodayTVs(language, page)
            airingTodayTVsLiveData.postValue(airingTodayTVs)
        }
    }

    fun fetchOnTheAirTVs(
        language: String?,
        page: Int?
    ) {
        ioScope.launch {
            val onTheAirTVs = tvRepository.getOnTheAirTVs(language, page)
            onTheAirTVsLiveData.postValue(onTheAirTVs)
        }
    }
}