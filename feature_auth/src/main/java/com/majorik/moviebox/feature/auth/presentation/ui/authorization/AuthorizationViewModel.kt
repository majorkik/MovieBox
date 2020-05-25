package com.majorik.moviebox.feature.auth.presentation.ui.authorization

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.majorik.library.base.models.results.ResultWrapper
import com.majorik.moviebox.feature.auth.domain.RequestTokenResponse
import com.majorik.moviebox.feature.auth.domain.ResponseSession
import com.majorik.moviebox.feature.auth.data.repository.AuthTmdbRepository
import kotlinx.coroutines.launch

internal class AuthorizationViewModel(private val authRepository: AuthTmdbRepository) : ViewModel() {
    var tmdbRequestTokenLiveData = MutableLiveData<RequestTokenResponse>()
    var tmdbSessionLiveData = MutableLiveData<ResponseSession>()
    var tmdbGuestSessionLiveData = MutableLiveData<RequestTokenResponse>()

    fun getRequestToken() {
        viewModelScope.launch {
            when (val response = authRepository.getRequestToken()) {
                is ResultWrapper.NetworkError -> {
                }

                is ResultWrapper.GenericError -> {
                }

                is ResultWrapper.Success -> {
                    tmdbRequestTokenLiveData.postValue(response.value)
                }
            }
        }
    }

    fun createSessionToken(requestToken: String) {
        viewModelScope.launch {
            when (val response = authRepository.createSession(requestToken)) {
                is ResultWrapper.NetworkError -> {
                }

                is ResultWrapper.GenericError -> {
                }

                is ResultWrapper.Success -> {
                    tmdbSessionLiveData.postValue(response.value)
                }
            }
        }
    }

    fun createGuestSession() {
        viewModelScope.launch {
            when (val response = authRepository.getRequestToken()) {
                is ResultWrapper.NetworkError -> {
                }

                is ResultWrapper.GenericError -> {
                }

                is ResultWrapper.Success -> {
                    tmdbGuestSessionLiveData.postValue(response.value)
                }
            }
        }
    }
}
