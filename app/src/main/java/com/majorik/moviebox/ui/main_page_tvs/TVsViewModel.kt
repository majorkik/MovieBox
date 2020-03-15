package com.majorik.moviebox.ui.main_page_tvs

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.majorik.data.repositories.TVRepository
import com.majorik.domain.tmdbModels.tv.TV
import kotlinx.coroutines.launch

class TVsViewModel(private val tvRepository: TVRepository) : ViewModel() {
    val popularTVsLiveData = MutableLiveData<MutableList<TV>>()
    val airTodayTVsLiveData = MutableLiveData<MutableList<TV>>()
    val onTheAirTVsLiveData = MutableLiveData<MutableList<TV>>()

    fun fetchPopularTVs(language: String?, page: Int?) {
        viewModelScope.launch {
            val response = tvRepository.getPopularTVs(language, page)

            response?.let { popularTVsLiveData.postValue(response) }
        }
    }

    fun fetchAirTodayTVs(language: String?, page: Int?) {
        viewModelScope.launch {
            val response = tvRepository.getAiringTodayTVs(language, page)

            response?.let { airTodayTVsLiveData.postValue(response) }
        }
    }

    fun fetchOnTheAirTVs(language: String?, page: Int?) {
        viewModelScope.launch {
            val response = tvRepository.getOnTheAirTVs(language, page)

            response?.let { onTheAirTVsLiveData.postValue(response) }
        }
    }
}
