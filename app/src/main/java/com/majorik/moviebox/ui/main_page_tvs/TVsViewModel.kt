package com.majorik.moviebox.ui.main_page_tvs

import androidx.lifecycle.MutableLiveData
import com.majorik.data.repositories.TVRepository
import com.majorik.domain.tmdbModels.tv.TV
import com.majorik.moviebox.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class TVsViewModel(private val tvRepository: TVRepository) : BaseViewModel() {
    val popularTVsLiveData = MutableLiveData<MutableList<TV>>()
    val airTodayTVsLiveData = MutableLiveData<MutableList<TV>>()
    val onTheAirTVsLiveData = MutableLiveData<MutableList<TV>>()

    fun fetchPopularTVs(language: String?, page: Int?) {
        ioScope.launch {
            val response = tvRepository.getPopularTVs(language, page)

            response?.let { popularTVsLiveData.postValue(response) }
        }
    }

    fun fetchAirTodayTVs(language: String?, page: Int?) {
        ioScope.launch {
            val response = tvRepository.getAiringTodayTVs(language, page)

            response?.let { airTodayTVsLiveData.postValue(response) }
        }
    }

    fun fetchOnTheAirTVs(language: String?, page: Int?) {
        ioScope.launch {
            val response = tvRepository.getOnTheAirTVs(language, page)

            response?.let { onTheAirTVsLiveData.postValue(response) }
        }
    }
}