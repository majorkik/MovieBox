package com.majorik.moviebox.ui.seasonDetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.majorik.data.repositories.TVRepository
import com.majorik.domain.tmdbModels.tv.TVSeasonDetails
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

            response?.let { seasonDetailsLiveData.postValue(response) }
        }
    }
}
