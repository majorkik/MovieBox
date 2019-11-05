package com.majorik.moviebox.ui.main_page_tvs

import androidx.lifecycle.MutableLiveData
import com.majorik.data.repositories.TVRepository
import com.majorik.domain.tmdbModels.tv.TVResponse
import com.majorik.moviebox.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class TVsViewModel(private val tvRepository: TVRepository) : BaseViewModel() {
    val popularTVsLiveData = MutableLiveData<MutableList<TVResponse.TV>>()
    val airTodayTVsLiveData = MutableLiveData<MutableList<TVResponse.TV>>()
    val onTheAirTVsLiveData = MutableLiveData<MutableList<TVResponse.TV>>()

    fun fetchPopularTVs(language: String?, page: Int?) {
        ioScope.launch {
            val popularTVs = tvRepository.getPopularTVs(language, page)

            popularTVsLiveData.postValue(popularTVs)
        }
    }

    fun fetchAirTodayTVs(language: String?, page: Int?) {
        ioScope.launch {
            val tvs = tvRepository.getAiringTodayTVs(language, page)
            airTodayTVsLiveData.postValue(tvs)
        }
    }

    fun fetchOnTheAirTVs(language: String?, page: Int?) {
        ioScope.launch {
            val tvs = tvRepository.getOnTheAirTVs(language, page)

            onTheAirTVsLiveData.postValue(tvs)
        }
    }
}