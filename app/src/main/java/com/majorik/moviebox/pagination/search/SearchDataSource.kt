package com.majorik.moviebox.pagination.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.majorik.data.repositories.SearchRepository
import com.majorik.domain.NetworkState
import com.majorik.domain.constants.AppConfig
import com.majorik.domain.tmdbModels.search.MultiSearchResponse.MultiSearchItem
import com.orhanobut.logger.Logger
import kotlinx.coroutines.*

class SearchDataSource(
    private val repository: SearchRepository,
    private val query: String,
    private val scope: CoroutineScope
) : PageKeyedDataSource<Int, MultiSearchItem>() {

    private var supervisorJob = SupervisorJob()
    private val networkState = MutableLiveData<NetworkState>()
    private var retryQuery: (() -> Any)? = null

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, MultiSearchItem>
    ) {
        retryQuery = { loadInitial(params, callback) }
        executeQuery(1) {
            callback.onResult(it, null, 2)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MultiSearchItem>) {
        val page = params.key
        retryQuery = { loadAfter(params, callback) }
        executeQuery(page) {
            callback.onResult(it, page + 1)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, MultiSearchItem>) {
        // no-op
    }

    private fun executeQuery(
        page: Int,
        callback: (List<MultiSearchItem>) -> Unit
    ) {
        networkState.postValue(NetworkState.RUNNING)
        scope.launch(getJobErrorHandler() + supervisorJob) {
            delay(200)
            val response = repository.multiSearch(AppConfig.REGION, query, page, false)
            retryQuery = null
            networkState.postValue(NetworkState.SUCCESS)
            response?.results?.let { callback(it) }
        }
    }

    private fun getJobErrorHandler() = CoroutineExceptionHandler { _, e ->
        Logger.e("Ошибка: $e")
        networkState.postValue(NetworkState.FAILED)
    }

    override fun invalidate() {
        super.invalidate()
        supervisorJob.cancelChildren(null)
    }

    fun getNetworkState(): LiveData<NetworkState> = networkState

    fun refresh() = this.invalidate()

    fun retryFailedQuery() {
        val previousQuery = retryQuery
        retryQuery = null
        previousQuery?.invoke()
    }
}
