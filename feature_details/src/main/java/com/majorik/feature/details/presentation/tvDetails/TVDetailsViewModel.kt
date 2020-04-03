package com.majorik.feature.details.presentation.tvDetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.majorik.feature.details.domain.tmdbModels.ApiResponse
import com.majorik.feature.details.domain.tmdbModels.account.AccountStates
import com.majorik.feature.details.domain.tmdbModels.request.RequestAddToWatchlist
import com.majorik.feature.details.domain.tmdbModels.request.RequestMarkAsFavorite
import com.majorik.feature.details.domain.tmdbModels.tv.TVDetails
import com.majorik.feature.details.presentation.AccountRepository
import com.majorik.feature.details.presentation.TVRepository
import com.majorik.library.base.models.results.ResultWrapper
import kotlinx.coroutines.launch

class TVDetailsViewModel(
    private val tvRepository: TVRepository,
    private val accountRepository: AccountRepository
) : ViewModel() {
    var tvDetailsLiveData = MutableLiveData<TVDetails>()
    var tvStatesLiveData = MutableLiveData<AccountStates?>()
    var responseFavoriteLiveData = MutableLiveData<ApiResponse>()
    var responseWatchlistLiveData = MutableLiveData<ApiResponse>()

    fun fetchTVDetails(
        tvId: Int,
        language: String?,
        appendToResponse: String?,
        imageLanguages: String?
    ) {
        viewModelScope.launch {
            val response =
                tvRepository.getTVById(tvId, language, appendToResponse, imageLanguages)

            when (response) {
                is ResultWrapper.NetworkError -> {
                }

                is ResultWrapper.GenericError -> {
                }

                is ResultWrapper.Success -> {
                    tvDetailsLiveData.postValue(response.value)
                }
            }
        }
    }

    fun fetchAccountStateForTV(tvId: Int, sessionId: String) {
        viewModelScope.launch {
            val response =
                tvRepository.getAccountStatesForTV(tvId, "", sessionId, "")

            when (response) {
                is ResultWrapper.NetworkError -> {
                }

                is ResultWrapper.GenericError -> {
                }

                is ResultWrapper.Success -> {
                    tvStatesLiveData.postValue(response.value)
                }
            }
        }
    }

    fun markTVIsFavorite(mediaId: Int, state: Boolean, sessionId: String) {
        viewModelScope.launch {
            val requestMarkAsFavorite = RequestMarkAsFavorite("tv", mediaId, state)
            val response = accountRepository.markIsFavorite(requestMarkAsFavorite, sessionId)

            when (response) {
                is ResultWrapper.NetworkError -> {
                }

                is ResultWrapper.GenericError -> {
                }

                is ResultWrapper.Success -> {
                    responseFavoriteLiveData.postValue(response.value)
                }
            }
        }
    }

    fun addTVToWatchlist(mediaId: Int, state: Boolean, sessionId: String) {
        viewModelScope.launch {
            val requestAddToWatchlist = RequestAddToWatchlist("tv", mediaId, state)
            val response = accountRepository.addToWatchlist(requestAddToWatchlist, sessionId)

            when (response) {
                is ResultWrapper.NetworkError -> {
                }

                is ResultWrapper.GenericError -> {
                }

                is ResultWrapper.Success -> {
                    responseWatchlistLiveData.postValue(response.value)
                }
            }
        }
    }
}
