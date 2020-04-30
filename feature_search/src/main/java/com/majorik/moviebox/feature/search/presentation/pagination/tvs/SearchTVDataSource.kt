package com.majorik.moviebox.feature.search.presentation.pagination.tvs

import com.majorik.moviebox.feature.search.data.repositories.SearchRepository
import com.majorik.moviebox.feature.search.domain.NetworkState
import com.majorik.moviebox.feature.search.domain.tmdbModels.tv.TV
import com.majorik.moviebox.feature.search.presentation.base.BaseSearchDataSource
import com.majorik.library.base.constants.AppConfig
import com.majorik.library.base.models.results.ResultWrapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

internal class SearchTVDataSource(
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

            when (response) {
                is ResultWrapper.Success -> {
                    networkState.postValue(NetworkState.SUCCESS)

                    callback(response.value.results)
                }
                else -> {
                    networkState.postValue(NetworkState.FAILED)
                }
            }
        }
    }
}
