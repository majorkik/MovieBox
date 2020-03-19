package com.majorik.moviebox.ui.first_start

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.majorik.data.repositories.TmdbAuthRepository
import com.majorik.domain.tmdbModels.auth.RequestTokenResponse
import com.majorik.domain.tmdbModels.auth.ResponseSession
import com.majorik.domain.tmdbModels.result.ResultWrapper
import kotlinx.coroutines.launch

class AuthViewModel(private val tmdbAuthRepository: TmdbAuthRepository) : ViewModel() {
    var tmdbRequestTokenLiveData = MutableLiveData<RequestTokenResponse>()
    var tmdbSessionLiveData = MutableLiveData<ResponseSession>()
    var tmdbGuestSessionLiveData = MutableLiveData<RequestTokenResponse>()

    fun getRequestToken() {
        viewModelScope.launch {
            val response = tmdbAuthRepository.getRequestToken()

            when (response) {
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
            val response = tmdbAuthRepository.createSession(requestToken)

            when (response) {
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
            val response = tmdbAuthRepository.getRequestToken()

            when (response) {
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
