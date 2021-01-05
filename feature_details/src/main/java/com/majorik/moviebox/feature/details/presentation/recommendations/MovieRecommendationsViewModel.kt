package com.majorik.moviebox.feature.details.presentation.recommendations

import androidx.lifecycle.viewModelScope
import com.majorik.library.base.models.results.ResultWrapper
import com.majorik.library.base.viewmodel.BaseAction
import com.majorik.library.base.viewmodel.BaseViewModelFlow
import com.majorik.library.base.viewmodel.BaseViewState
import com.majorik.moviebox.feature.details.data.repositories.MovieRepository
import com.majorik.moviebox.feature.details.domain.tmdbModels.movie.Movie
import com.majorik.moviebox.feature.details.domain.tmdbModels.movie.MovieResponse
import com.majorik.moviebox.feature.details.presentation.recommendations.MovieRecommendationsViewModel.Action
import com.majorik.moviebox.feature.details.presentation.recommendations.MovieRecommendationsViewModel.ViewState
import kotlinx.coroutines.launch

class MovieRecommendationsViewModel(private val repository: MovieRepository) :
    BaseViewModelFlow<ViewState, Action>(ViewState()) {

    var movieId: Int? = null
    var page = 1
    private var totalPages = 1

    fun fetchMovies() = viewModelScope.launch {
        when (val result =
            repository.getRecommendations(movieId = movieId!!, language = null, page = page, region = null)) {

            is ResultWrapper.NetworkError -> sendAction(Action.NetworkError)
            is ResultWrapper.GenericError -> sendAction(Action.ContentLoadFailure)
            is ResultWrapper.Success -> {
                totalPages = result.value.totalPages

                sendAction(Action.ContentLoadSuccess(result.value))

                page++
            }
        }
    }

    fun nextPage() {
        if (page <= totalPages) {
            fetchMovies()
        }
    }

    data class ViewState(
        val items: List<Movie> = emptyList(),
        val isLoading: Boolean = true,
        val isNetworkError: Boolean = false,
        val isGenericError: Boolean = false,
        val currentPage: Int = 1
    ) : BaseViewState

    sealed class Action : BaseAction {
        object ContentIsLoading : Action() // пока контент грузится
        data class ContentLoadSuccess(val movieResponse: MovieResponse) : Action()
        object ContentLoadFailure : Action()

        object NetworkError : Action() // если нет интернета или другая ошибка сети
    }

    override fun onReduceState(viewAction: Action) = when (viewAction) {
        is Action.ContentIsLoading -> state.copy(isLoading = true, isNetworkError = false, isGenericError = false)
        is Action.ContentLoadSuccess -> state.copy(
            isLoading = false,
            items = state.items + viewAction.movieResponse.results
        )
        is Action.ContentLoadFailure -> state.copy(isLoading = false, isNetworkError = false, isGenericError = true)
        is Action.NetworkError -> state.copy(isLoading = false, isNetworkError = true, isGenericError = false)
    }
}
