package com.majorik.moviebox.ui.movieDetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.majorik.data.repositories.AccountRepository
import com.majorik.data.repositories.MovieRepository
import com.majorik.domain.tmdbModels.ApiResponse
import com.majorik.domain.tmdbModels.account.AccountStates
import com.majorik.domain.tmdbModels.movie.MovieDetails
import com.majorik.domain.tmdbModels.request.RequestAddToWatchlist
import com.majorik.domain.tmdbModels.request.RequestMarkAsFavorite
import com.majorik.moviebox.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val movieRepository: MovieRepository,
    private val accountRepository: AccountRepository
) : BaseViewModel() {
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
            val movieDetails =
                movieRepository.getMovieById(movieId, language, appendToResponse, imageLanguages)

            movieDetailsLiveData.postValue(movieDetails)
        }
    }

    fun fetchAccountStateForMovie(movieId: Int, sessionId: String) {
        viewModelScope.launch {
            val movieStates =
                movieRepository.getAccountStatesForMovie(movieId, sessionId, guestSessionId = null)

            movieStatesLiveData.postValue(movieStates)
        }
    }

    fun markMovieIsFavorite(mediaId: Int, state: Boolean, sessionId: String) {
        viewModelScope.launch {
            val requestMarkAsFavorite = RequestMarkAsFavorite("movie", mediaId, state)
            val response = accountRepository.markIsFavorite(requestMarkAsFavorite, sessionId)

            responseFavoriteLiveData.postValue(response)
        }
    }

    fun addMovieToWatchlist(mediaId: Int, state: Boolean, sessionId: String) {
        viewModelScope.launch {
            val requestAddToWatchlist = RequestAddToWatchlist("movie", mediaId, state)
            val response = accountRepository.addToWatchlist(requestAddToWatchlist, sessionId)

            responseWatchlistLiveData.postValue(response)
        }
    }
}