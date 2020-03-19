package com.majorik.moviebox.ui.main_page_tvs

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.majorik.data.repositories.TVRepository
import com.majorik.domain.tmdbModels.result.ResultWrapper
import com.majorik.domain.tmdbModels.tv.TV
import kotlinx.coroutines.launch

class TVsViewModel(private val tvRepository: TVRepository) : ViewModel() {
    val popularTVsLiveData = MutableLiveData<List<TV>>()
    val airTodayTVsLiveData = MutableLiveData<List<TV>>()
    val onTheAirTVsLiveData = MutableLiveData<List<TV>>()

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
}
