package com.majorik.moviebox.ui.first_start

import androidx.lifecycle.MutableLiveData
import com.majorik.data.repositories.TmdbAuthRepository
import com.majorik.domain.tmdbModels.auth.RequestTokenResponse
import com.majorik.domain.tmdbModels.auth.ResponseSession
import com.majorik.moviebox.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class AuthViewModel(private val tmdbAuthRepository: TmdbAuthRepository) : BaseViewModel() {
    var tmdbRequestTokenLiveData = MutableLiveData<RequestTokenResponse>()
    var tmdbSessionLiveData = MutableLiveData<ResponseSession>()
    var tmdbGuestSessionLiveData = MutableLiveData<RequestTokenResponse>()

    fun getRequestToken() {
        ioScope.launch {
            val response = tmdbAuthRepository.getRequestToken()

            response?.let { tmdbRequestTokenLiveData.postValue(it) }
        }
    }

    fun createSessionToken(requestToken: String) {
        ioScope.launch {
            val response = tmdbAuthRepository.createSession(requestToken)

            response?.let { tmdbSessionLiveData.postValue(it) }
        }
    }

    fun createGuestSession() {
        ioScope.launch {
            val response = tmdbAuthRepository.getRequestToken()

            response?.let { tmdbGuestSessionLiveData.postValue(it) }
        }
    }
}
