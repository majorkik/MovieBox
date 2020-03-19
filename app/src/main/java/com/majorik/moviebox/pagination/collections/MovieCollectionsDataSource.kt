package com.majorik.moviebox.pagination.collections

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.majorik.data.repositories.MovieRepository
import com.majorik.domain.NetworkState
import com.majorik.domain.constants.AppConfig
import com.majorik.domain.enums.movie.MovieCollectionType
import com.majorik.domain.tmdbModels.movie.Movie
import com.majorik.domain.tmdbModels.movie.MovieResponse
import com.majorik.domain.tmdbModels.result.ResultWrapper
import kotlinx.coroutines.*
import timber.log.Timber

class MovieCollectionsDataSource(
    private val repository: MovieRepository,
    private val scope: CoroutineScope,
    private val movieCollectionType: MovieCollectionType
) : PageKeyedDataSource<Int, Movie>() {
    private var supervisorJob = SupervisorJob()
    private val networkState = MutableLiveData<NetworkState>()
    private var retryQuery: (() -> Any)? = null

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        retryQuery = { loadInitial(params, callback) }
        executeQuery(1) {
            callback.onResult(it, null, 2)
        }
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Movie>
    ) {
        val page = params.key
        retryQuery = { loadAfter(params, callback) }
        executeQuery(page) {
            callback.onResult(it, page + 1)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        // no-op
    }

    private fun executeQuery(
        page: Int,
        callback: (List<Movie>) -> Unit
    ) {
        networkState.postValue(NetworkState.RUNNING)
        scope.launch(getJobErrorHandler() + supervisorJob) {
            val response = getCurrentQuery(AppConfig.REGION, page, null)
            retryQuery = null
            networkState.postValue(NetworkState.SUCCESS)
            response?.let { callback(it) }
        }
    }

    private fun getJobErrorHandler() = CoroutineExceptionHandler { _, e ->
        Timber.e(MovieCollectionsDataSource::class.java.simpleName, "Ошибка: $e")
        networkState.postValue(NetworkState.FAILED)
    }

    override fun invalidate() {
        super.invalidate()
        supervisorJob.cancelChildren(null)
    }

    private suspend fun getCurrentQuery(
        language: String?,
        page: Int?,
        region: String?
    ): List<Movie>? {
        val response = when (movieCollectionType) {
            MovieCollectionType.POPULAR -> {
                repository.getPopularMovies(language, page, region)
            }
            MovieCollectionType.TOP_RATED -> {
                repository.getTopRatedMovies(language, page, region)
            }
            MovieCollectionType.NOW_PLAYING -> {
                repository.getNowPlayingMovies(language, page, region)
            }
            MovieCollectionType.UPCOMING -> {
                repository.getUpcomingMovies(language, page, region)
            }
        }

        return getResult(response)
    }

    fun getNetworkState(): LiveData<NetworkState> = networkState

    fun refresh() = this.invalidate()

    fun retryFailedQuery() {
        val previousQuery = retryQuery
        retryQuery = null
        previousQuery?.invoke()
    }

    private fun getResult(response: ResultWrapper<MovieResponse>): List<Movie> {
        return when (response) {
            is ResultWrapper.Success -> response.value.results
            else -> emptyList()
        }
    }
}
