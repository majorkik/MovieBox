package com.majorik.moviebox.feature.details.presentation.tvDetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.majorik.moviebox.feature.details.domain.tmdbModels.ApiResponse
import com.majorik.moviebox.feature.details.domain.tmdbModels.account.AccountStates
import com.majorik.moviebox.feature.details.domain.tmdbModels.request.RequestAddToWatchlist
import com.majorik.moviebox.feature.details.domain.tmdbModels.request.RequestMarkAsFavorite
import com.majorik.moviebox.feature.details.domain.tmdbModels.tv.TVDetails
import com.majorik.moviebox.feature.details.data.repositories.AccountRepository
import com.majorik.moviebox.feature.details.data.repositories.TVRepository
import com.majorik.library.base.models.results.ResultWrapper
import com.majorik.library.base.storage.CredentialsPrefsManager
import kotlinx.coroutines.launch

class TVDetailsViewModel(
    private val tvRepository: TVRepository,
    private val accountRepository: AccountRepository,
    private val credentialsManager: CredentialsPrefsManager
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

    fun fetchAccountStateForTV(tvId: Int) {
        viewModelScope.launch {
            val response =
                tvRepository.getAccountStatesForTV(tvId, "", credentialsManager.getTmdbSessionID() ?: "", "")

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

    fun markTVIsFavorite(mediaId: Int, state: Boolean) {
        viewModelScope.launch {
            val requestMarkAsFavorite = RequestMarkAsFavorite("tv", mediaId, state)
            val response =
                accountRepository.markIsFavorite(requestMarkAsFavorite, credentialsManager.getTmdbSessionID() ?: "")

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

    fun addTVToWatchlist(mediaId: Int, state: Boolean) {
        viewModelScope.launch {
            val requestAddToWatchlist = RequestAddToWatchlist("tv", mediaId, state)
            val response =
                accountRepository.addToWatchlist(requestAddToWatchlist, credentialsManager.getTmdbSessionID() ?: "")

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
