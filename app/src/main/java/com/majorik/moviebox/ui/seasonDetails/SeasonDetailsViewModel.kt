package com.majorik.moviebox.ui.seasonDetails

import androidx.lifecycle.MutableLiveData
import com.majorik.data.repositories.TVRepository
import com.majorik.domain.models.tv.TVSeasonDetails
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
            val seasonDetails =
                tvRepository.getTVSeasonDetails(tvId, seasonNumber, language, appendToResponse)

            seasonDetailsLiveData.postValue(seasonDetails)
        }
    }
}