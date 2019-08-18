package com.majorik.moviebox.ui.login

import androidx.lifecycle.MutableLiveData
import com.majorik.data.repositories.TmdbAuthRepository
import com.majorik.data.repositories.TraktAuthRepository
import com.majorik.domain.tmdbModels.RequestTokenResponse
import com.majorik.domain.tmdbModels.ResponseSession
import com.majorik.domain.traktModels.oauth.OAuthToken
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
            tmdbSessionLiveData.postValue(response)
        }
    }
}