package com.majorik.moviebox.feature.details.presentation.movieDetails

import androidx.lifecycle.viewModelScope
import com.majorik.library.base.enums.TmdbMediaType
import com.majorik.library.base.models.results.ResultWrapper
import com.majorik.library.base.storage.CredentialsPrefsManager
import com.majorik.library.base.viewmodel.BaseAction
import com.majorik.library.base.viewmodel.BaseViewModel
import com.majorik.library.base.viewmodel.BaseViewState
import com.majorik.moviebox.feature.details.data.repositories.AccountRepository
import com.majorik.moviebox.feature.details.data.repositories.MovieRepository
import com.majorik.moviebox.feature.details.domain.tmdbModels.account.AccountStates
import com.majorik.moviebox.feature.details.domain.tmdbModels.movie.MovieDetails
import com.majorik.moviebox.feature.details.domain.tmdbModels.request.RequestAddToWatchlist
import com.majorik.moviebox.feature.details.domain.tmdbModels.request.RequestMarkAsFavorite
import kotlinx.coroutines.launch

internal class MovieDetailsViewModel(
    private val movieRepository: MovieRepository,
    private val accountRepository: AccountRepository,
    credentialsManager: CredentialsPrefsManager
) : BaseViewModel<MovieDetailsViewModel.ViewState, MovieDetailsViewModel.Action>(ViewState()) {

    private val tmdbSessionId = credentialsManager.getTmdbSessionID() ?: ""

    fun fetchMovieDetails(
        movieId: Int,
        language: String?,
        appendToResponse: String?,
        imageLanguages: String?
    ) {
        sendAction(Action.ContentIsLoading)
        viewModelScope.launch {
            val response =
                movieRepository.getMovieById(movieId, language, appendToResponse, imageLanguages)

            when (response) {
                is ResultWrapper.NetworkError -> sendAction(Action.NetworkError)
                is ResultWrapper.GenericError -> sendAction(Action.ContentLoadFailure)
                is ResultWrapper.Success -> {
                    fetchAccountStateForMovie(movieId)
                    sendAction(Action.ContentLoadSuccess(response.value))
                }
            }
        }
    }

    private fun fetchAccountStateForMovie(movieId: Int) {
        viewModelScope.launch {
            val response = movieRepository.getAccountStatesForMovie(
                movieId,
                tmdbSessionId,
                guestSessionId = null
            )
            when (response) {
                is ResultWrapper.NetworkError -> sendAction(Action.NetworkError)
                is ResultWrapper.GenericError -> sendAction(Action.AccountStateLoadFailure)
                is ResultWrapper.Success -> sendAction(Action.AccountStateLoadSuccess(response.value))
            }
        }
    }

    fun markMovieIsFavorite(state: Boolean) {
        viewModelScope.launch {
            val id = stateLiveData.value?.details?.id

            if (id != null) {
                val requestMarkAsFavorite = RequestMarkAsFavorite(TmdbMediaType.MOVIE.path, id, state)
                val response =
                    accountRepository.markIsFavorite(requestMarkAsFavorite, tmdbSessionId)

                when (response) {
                    is ResultWrapper.NetworkError -> sendAction(Action.NetworkError)
                    is ResultWrapper.GenericError -> sendAction(Action.FavoriteLoadFailure)
                    is ResultWrapper.Success -> sendAction(Action.FavoriteLoad(state))
                }
            }
        }
    }

    fun addMovieToWatchlist(state: Boolean) {
        viewModelScope.launch {
            val id = stateLiveData.value?.details?.id

            if (id != null) {
                val requestAddToWatchlist = RequestAddToWatchlist("movie", id, state)

                when (accountRepository.addToWatchlist(requestAddToWatchlist, tmdbSessionId)) {
                    is ResultWrapper.NetworkError -> sendAction(Action.NetworkError)
                    is ResultWrapper.GenericError -> sendAction(Action.WatchlistLoadFailure)
                    is ResultWrapper.Success -> sendAction(Action.WatchlistLoad(state))
                }
            }
        }
    }

    /**
     * ViewState
     *
     * @param isContentLoaded - когда надо обновить детали
     *
     * [isContentLoaded] - когда надо обновить детали
     * [details] - детали фильма
     * [isLoading] - когда грузяться какие-то данные
     * [isError] - когда произошла ошибка
     * [networkError] - когда произошла ошибка сети (например: пропал интернет)
     * [isFavorite] и [isWatchlist] - добавлен фильм в списки или нет
     */
    internal data
    class ViewState(
        val isContentLoaded: Boolean? = false,
        val details: MovieDetails? = null,

        val isLoading: Boolean = true,
        val isError: Boolean = false,
        val networkError: Boolean = false,
        val isFavorite: Boolean? = null,
        val isWatchlist: Boolean? = null
    ) : BaseViewState

    /**
     * Actions
     */

    internal sealed class Action : BaseAction {
        object ContentIsLoading : Action() // пока контент грузится
        data class ContentLoadSuccess(val details: MovieDetails) : Action()
        object ContentLoadFailure : Action()

        data class AccountStateLoadSuccess(val accountStates: AccountStates) : Action()
        object AccountStateLoadFailure : Action()

        data class FavoriteLoad(val currentState: Boolean) : Action()
        object FavoriteLoadFailure : Action()

        data class WatchlistLoad(val currentState: Boolean) : Action()
        object WatchlistLoadFailure : Action()

        object NetworkError : Action() // если нет интернета или другая ошибка сети
    }

    override fun onReduceState(viewAction: Action) = when (viewAction) {
        is Action.ContentIsLoading -> state.copy(
            isLoading = true,
            isContentLoaded = false,
            isError = false,
            networkError = false,
        )

        is Action.ContentLoadSuccess -> state.copy(
            isLoading = false,
            isError = false,
            isContentLoaded = true,
            networkError = false,
            details = viewAction.details
        )

        is Action.ContentLoadFailure -> state.copy(
            isLoading = false,
            isError = true,
            isContentLoaded = false,
            networkError = false,
        )

        is Action.NetworkError -> state.copy(
            isLoading = false,
            isContentLoaded = false,
            isError = true,
            networkError = true
        )

        is Action.FavoriteLoad -> state.copy(isContentLoaded = false, isFavorite = viewAction.currentState)
        is Action.FavoriteLoadFailure -> state.copy(isContentLoaded = false)

        is Action.WatchlistLoad -> state.copy(isContentLoaded = false, isWatchlist = viewAction.currentState)
        is Action.WatchlistLoadFailure -> state.copy(isContentLoaded = false)

        is Action.AccountStateLoadSuccess -> state.copy(
            isFavorite = viewAction.accountStates.favorite,
            isWatchlist = viewAction.accountStates.watchlist,
            isContentLoaded = false,
        )
        is Action.AccountStateLoadFailure -> state.copy(isFavorite = null, isWatchlist = null, isContentLoaded = false)
    }
}
