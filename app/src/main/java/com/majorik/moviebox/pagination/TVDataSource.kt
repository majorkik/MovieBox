package com.majorik.moviebox.pagination

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.majorik.data.repositories.TVRepository
import com.majorik.domain.NetworkState
import com.majorik.domain.tmdbModels.tv.TVCollectionType
import com.majorik.domain.tmdbModels.tv.TVResponse
import kotlinx.coroutines.*
import timber.log.Timber

class TVDataSource(
    private val repository: TVRepository,
    private val scope: CoroutineScope,
    private val tvCollectionType     : TVCollectionType
) : PageKeyedDataSource<Int, TVResponse.TV>() {
    private var supervisorJob = SupervisorJob()
    private val networkState = MutableLiveData<NetworkState>()
    private var retryQuery: (() -> Any)? = null

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, TVResponse.TV>
    ) {
        retryQuery = { loadInitial(params, callback) }
        executeQuery(1) {
            callback.onResult(it, null, 2)
        }
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, TVResponse.TV>
    ) {
        val page = params.key
        retryQuery = { loadAfter(params, callback) }
        executeQuery(page) {
            callback.onResult(it, page + 1)
        }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, TVResponse.TV>
    ) {
    }

    private fun executeQuery(
        page: Int,
        callback: (List<TVResponse.TV>) -> Unit
    ) {
        networkState.postValue(NetworkState.RUNNING)
        scope.launch(getJobErrorHandler() + supervisorJob) {
            val searchResults = getCurrentQuery("ru", page)
            retryQuery = null
            networkState.postValue(NetworkState.SUCCESS)
            searchResults?.let { callback(it) }
        }
    }


    private fun getJobErrorHandler() = CoroutineExceptionHandler { _, e ->
        Timber.e(TVDataSource::class.java.simpleName, "Ошибка: $e")
        networkState.postValue(NetworkState.FAILED)
    }

    override fun invalidate() {
        super.invalidate()
        supervisorJob.cancelChildren(null)
    }

    private suspend fun getCurrentQuery(
        language: String?,
        page: Int?
    ): MutableList<TVResponse.TV>? =
        when (tvCollectionType) {
            TVCollectionType.POPULAR -> {
                repository.getPopularTVs(language, page)
            }
            TVCollectionType.TOP_RATED -> {
                repository.getTopRatedTVs(language, page)
            }
            TVCollectionType.AIRING_TODAY -> {
                repository.getAiringTodayTVs(language, page)
            }
            TVCollectionType.ON_THE_AIR -> {
                repository.getOnTheAirTVs(language, page)
            }
        }

    fun getNetworkState(): LiveData<NetworkState> = networkState

    fun refresh() = this.invalidate()

    fun retryFailedQuery() {
        val previousQuery = retryQuery
        retryQuery = null
        previousQuery?.invoke()
    }

}