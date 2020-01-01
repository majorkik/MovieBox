package com.majorik.moviebox.ui.login

import androidx.lifecycle.MutableLiveData
import com.majorik.data.repositories.TmdbAuthRepository
import com.majorik.domain.tmdbModels.auth.RequestTokenResponse
import com.majorik.domain.tmdbModels.auth.ResponseSession
import com.majorik.moviebox.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class LoginPageViewModel(private val tmdbAuthRepository: TmdbAuthRepository) : BaseViewModel() {
    var tmdbRequestTokenLiveData = MutableLiveData<RequestTokenResponse>()
    var tmdbSessionLiveData = MutableLiveData<ResponseSession>()

    fun getRequestToken() {
        ioScope.launch {
            val response = tmdbAuthRepository.getRequestToken()

            response?.let { tmdbRequestTokenLiveData.postValue(it) }
        }
    }

    fun createSessionToken(requestToken: String) {
        ioScope.launch {
            val response = tmdbAuthRepository.createSession(requestToken)

            response?.let { tmdbSessionLiveData.postValue(response) }
        }
    }
}