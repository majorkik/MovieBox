package com.majorik.moviebox.ui.tvDetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.majorik.data.repositories.AccountRepository
import com.majorik.data.repositories.TVRepository
import com.majorik.domain.tmdbModels.ApiResponse
import com.majorik.domain.tmdbModels.account.AccountStates
import com.majorik.domain.tmdbModels.request.RequestAddToWatchlist
import com.majorik.domain.tmdbModels.request.RequestMarkAsFavorite
import com.majorik.domain.tmdbModels.result.ResultWrapper
import com.majorik.domain.tmdbModels.tv.TVDetails
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
