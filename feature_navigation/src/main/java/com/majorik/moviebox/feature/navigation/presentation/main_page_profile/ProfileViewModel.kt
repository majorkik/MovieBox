package com.majorik.moviebox.feature.navigation.presentation.main_page_profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.majorik.library.base.models.results.ResultWrapper
import com.majorik.library.base.storage.CredentialsPrefsManager
import com.majorik.moviebox.feature.navigation.data.repositories.AccountRepository
import com.majorik.moviebox.feature.navigation.domain.tmdbModels.account.AccountDetails
import com.majorik.moviebox.feature.navigation.domain.tmdbModels.movie.Movie
import com.majorik.moviebox.feature.navigation.domain.tmdbModels.tv.TV
import com.orhanobut.logger.Logger
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val accountRepository: AccountRepository,
    private val credentialsPrefsManager: CredentialsPrefsManager
) : ViewModel() {

    val accountDetails = MutableLiveData<AccountDetails>()
    val favoriteMovies = MutableLiveData<List<Movie>>()
    val favoriteTVs = MutableLiveData<List<TV>>()
    val watchlistMovies = MutableLiveData<List<Movie>>()
    val watchlistTVs = MutableLiveData<List<TV>>()

    fun getAccountDetails() {
        val sessionId = credentialsPrefsManager.getTmdbSessionID() ?: ""

        viewModelScope.launch {
            when (val response = accountRepository.getAccountDetails(sessionId)) {
                is ResultWrapper.NetworkError -> {
                }

                is ResultWrapper.GenericError -> {
                }

                is ResultWrapper.Success -> {
                    Logger.i(response.value.toString())
                    accountDetails.postValue(response.value)
                }
            }
        }
    }

    fun fetchFavoriteMovies(language: String?, page: Int?) {
        val sessionId = credentialsPrefsManager.getTmdbSessionID() ?: ""

        viewModelScope.launch {
            when (val response = accountRepository.getFavoriteMovies(language, sessionId, "created_at.asc", page)) {
                is ResultWrapper.NetworkError -> {
                }

                is ResultWrapper.GenericError -> {
                }

                is ResultWrapper.Success -> {
                    favoriteMovies.postValue(response.value.results)
                }
            }
        }
    }

    fun fetchFavoriteTVs(language: String?, page: Int?) {
        val sessionId = credentialsPrefsManager.getTmdbSessionID() ?: ""

        viewModelScope.launch {
            when (val response = accountRepository.getFavoriteTVs(language, sessionId, "created_at.asc", page)) {
                is ResultWrapper.NetworkError -> {
                }

                is ResultWrapper.GenericError -> {
                }

                is ResultWrapper.Success -> {
                    favoriteTVs.postValue(response.value.results)
                }
            }
        }
    }

    fun fetchWatchlistMovies(language: String?, page: Int?) {
        val sessionId = credentialsPrefsManager.getTmdbSessionID() ?: ""

        viewModelScope.launch {
            when (val response = accountRepository.getWatchlistMovies(language, sessionId, "created_at.asc", page)) {
                is ResultWrapper.NetworkError -> {
                }

                is ResultWrapper.GenericError -> {
                }

                is ResultWrapper.Success -> {
                    watchlistMovies.postValue(response.value.results)
                }
            }
        }
    }

    fun fetchWatchlistTVs(language: String?, page: Int?) {
        val sessionId = credentialsPrefsManager.getTmdbSessionID() ?: ""

        viewModelScope.launch {
            when (val response = accountRepository.getWatchlistTVs(language, sessionId, "created_at.asc", page)) {
                is ResultWrapper.NetworkError -> {
                }

                is ResultWrapper.GenericError -> {
                }

                is ResultWrapper.Success -> {
                    watchlistTVs.postValue(response.value.results)
                }
            }
        }
    }
}
