package com.majorik.moviebox.feature.details.presentation.movieDetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.majorik.moviebox.feature.details.domain.tmdbModels.ApiResponse
import com.majorik.moviebox.feature.details.domain.tmdbModels.account.AccountStates
import com.majorik.moviebox.feature.details.domain.tmdbModels.movie.MovieDetails
import com.majorik.moviebox.feature.details.domain.tmdbModels.request.RequestAddToWatchlist
import com.majorik.moviebox.feature.details.domain.tmdbModels.request.RequestMarkAsFavorite
import com.majorik.moviebox.feature.details.presentation.AccountRepository
import com.majorik.moviebox.feature.details.presentation.MovieRepository
import com.majorik.library.base.models.results.ResultWrapper
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val movieRepository: MovieRepository,
    private val accountRepository: AccountRepository
) : ViewModel() {
    var movieDetailsLiveData = MutableLiveData<MovieDetails>()
    var movieStatesLiveData = MutableLiveData<AccountStates?>()
    var responseFavoriteLiveData = MutableLiveData<ApiResponse>()
    var responseWatchlistLiveData = MutableLiveData<ApiResponse>()

    fun fetchMovieDetails(
        movieId: Int,
        language: String?,
        appendToResponse: String?,
        imageLanguages: String?
    ) {
        viewModelScope.launch {
            val response =
                movieRepository.getMovieById(movieId, language, appendToResponse, imageLanguages)

            when (response) {
                is ResultWrapper.NetworkError -> {
                }
                is ResultWrapper.GenericError -> {
                }
                is ResultWrapper.Success -> {
                    movieDetailsLiveData.postValue(response.value)
                }
            }
        }
    }

    fun fetchAccountStateForMovie(movieId: Int, sessionId: String) {
        viewModelScope.launch {
            val response =
                movieRepository.getAccountStatesForMovie(movieId, sessionId, guestSessionId = null)

            when (response) {
                is ResultWrapper.NetworkError -> {
                }
                is ResultWrapper.GenericError -> {
                }
                is ResultWrapper.Success -> {
                    movieStatesLiveData.postValue(response.value)
                }
            }
        }
    }

    fun markMovieIsFavorite(mediaId: Int, state: Boolean, sessionId: String) {
        viewModelScope.launch {
            val requestMarkAsFavorite = RequestMarkAsFavorite("movie", mediaId, state)
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

    fun addMovieToWatchlist(mediaId: Int, state: Boolean, sessionId: String) {
        viewModelScope.launch {
            val requestAddToWatchlist = RequestAddToWatchlist("movie", mediaId, state)
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
