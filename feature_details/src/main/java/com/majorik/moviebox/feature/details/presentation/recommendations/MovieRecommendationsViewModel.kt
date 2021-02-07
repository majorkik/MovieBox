package com.majorik.moviebox.feature.details.presentation.recommendations

import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.majorik.library.base.models.results.ResultWrapper
import com.majorik.library.base.viewmodel.BaseAction
import com.majorik.library.base.viewmodel.BaseViewModelFlow
import com.majorik.library.base.viewmodel.BaseViewState
import com.majorik.moviebox.feature.details.data.repositories.MovieRepository
import com.majorik.moviebox.feature.details.domain.tmdbModels.movie.Movie
import com.majorik.moviebox.feature.details.domain.tmdbModels.movie.MovieResponse
import com.majorik.moviebox.feature.details.presentation.recommendations.MovieRecommendationsViewModel.Action
import com.majorik.moviebox.feature.details.presentation.recommendations.MovieRecommendationsViewModel.ViewState
import com.orhanobut.logger.Logger
import kotlinx.coroutines.launch

class MovieRecommendationsViewModel(private val repository: MovieRepository) :
    BaseViewModelFlow<ViewState, Action>(ViewState()) {

    var movieId: Int = 0
    var page = 1
    private var totalPages = 1

    var dataSource: RecommendationsMoviesPagingDataSource? = null

    var moviesFlow = Pager(PagingConfig(pageSize = 20)) {
        RecommendationsMoviesPagingDataSource(repository, movieId).also { dataSource = it }
    }.flow.cachedIn(viewModelScope)

    fun fetchMovies() = viewModelScope.launch {
        when (
            val result =
                repository.getRecommendations(movieId = movieId!!, language = null, page = page, region = null)
        ) {

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

class RecommendationsMoviesPagingDataSource(
    private val repository: MovieRepository,
    private val movieId: Int
) : PagingSource<Int, Movie>() {

    private var totalPages: Int? = null

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, Movie> {
        // Start refresh at page 1 if undefined.
        val nextPageNumber = params.key ?: 1

        if (totalPages != null && nextPageNumber > totalPages!!) {
            return LoadResult.Error(Exception("Max page. nextPage: $nextPageNumber, maxPage: $totalPages"))
        }

        return when (
            val response =
                repository.getRecommendations(movieId = movieId, language = null, page = nextPageNumber, region = null)
        ) {
            is ResultWrapper.GenericError -> {
                Logger.e("Generic Error")

                LoadResult.Error(
                    Exception(
                        "Generic Error  ${response.error?.statusMessage} ${response.error?.statusCode}"
                    )
                )
            }

            is ResultWrapper.NetworkError -> {
                Logger.e("Network Error")
                LoadResult.Error(Exception("Network Error}"))
            }

            is ResultWrapper.Success -> {
                totalPages = response.value.totalPages

                LoadResult.Page(
                    data = response.value.results,
                    prevKey = if (nextPageNumber == 1) null else nextPageNumber - 1,
                    nextKey = nextPageNumber + 1
                )
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? = null
}
