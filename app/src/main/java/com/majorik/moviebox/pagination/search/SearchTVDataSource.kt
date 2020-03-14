package com.majorik.moviebox.pagination.search

import com.majorik.data.repositories.SearchRepository
import com.majorik.domain.NetworkState
import com.majorik.domain.constants.AppConfig
import com.majorik.domain.tmdbModels.tv.TV
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchTVDataSource(
    private val repository: SearchRepository,
    private val query: String,
    private val scope: CoroutineScope
) : BaseSearchDataSource<TV>(repository, query, scope) {

    override fun executeQuery(page: Int, callback: (List<TV>) -> Unit) {
        networkState.postValue(NetworkState.RUNNING)
        scope.launch(getJobErrorHandler() + supervisorJob) {
            delay(200)
            val response = repository.searchTVs(AppConfig.REGION, query, page, null)
            retryQuery = null
            networkState.postValue(NetworkState.SUCCESS)
            response?.results?.let { callback(it) }
        }
    }
}
