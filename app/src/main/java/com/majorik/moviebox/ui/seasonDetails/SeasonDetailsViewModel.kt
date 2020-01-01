package com.majorik.moviebox.ui.seasonDetails

import androidx.lifecycle.MutableLiveData
import com.majorik.data.repositories.TVRepository
import com.majorik.domain.tmdbModels.tv.TVSeasonDetails
import com.majorik.moviebox.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class SeasonDetailsViewModel(private val tvRepository: TVRepository) : BaseViewModel() {
    var seasonDetailsLiveData = MutableLiveData<TVSeasonDetails>()

    fun fetchSeasonDetails(
        tvId: Int,
        seasonNumber: Int,
        language: String?,
        appendToResponse: String?
    ) {
        ioScope.launch {
            val response =
                tvRepository.getTVSeasonDetails(tvId, seasonNumber, language, appendToResponse)

            response?.let { seasonDetailsLiveData.postValue(response) }
        }
    }
}