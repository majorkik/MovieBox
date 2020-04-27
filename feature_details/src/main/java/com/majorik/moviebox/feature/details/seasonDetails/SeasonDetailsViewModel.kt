package com.majorik.moviebox.feature.details.seasonDetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.majorik.moviebox.feature.details.domain.tmdbModels.tv.TVSeasonDetails
import com.majorik.moviebox.feature.details.presentation.TVRepository
import com.majorik.library.base.models.results.ResultWrapper
import kotlinx.coroutines.launch

class SeasonDetailsViewModel(private val tvRepository: TVRepository) : ViewModel() {
    var seasonDetailsLiveData = MutableLiveData<TVSeasonDetails>()

    fun fetchSeasonDetails(
        tvId: Int,
        seasonNumber: Int,
        language: String?,
        appendToResponse: String?
    ) {
        viewModelScope.launch {
            val response =
                tvRepository.getTVSeasonDetails(tvId, seasonNumber, language, appendToResponse)

            when (response) {
                is ResultWrapper.NetworkError -> {
                }

                is ResultWrapper.GenericError -> {
                }

                is ResultWrapper.Success -> {
                    seasonDetailsLiveData.postValue(response.value)
                }
            }
        }
    }
}
