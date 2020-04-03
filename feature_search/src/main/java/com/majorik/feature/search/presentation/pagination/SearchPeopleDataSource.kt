package com.majorik.feature.search.presentation.pagination

import com.majorik.feature.search.data.repositories.SearchRepository
import com.majorik.feature.search.domain.NetworkState
import com.majorik.feature.search.domain.tmdbModels.person.Person
import com.majorik.feature.search.presentation.base.BaseSearchDataSource
import com.majorik.library.base.constants.AppConfig
import com.majorik.library.base.models.results.ResultWrapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchPeopleDataSource(
    private val repository: SearchRepository,
    private val query: String,
    private val scope: CoroutineScope
) : BaseSearchDataSource<Person>(repository, query, scope) {
    override fun executeQuery(page: Int, callback: (List<Person>) -> Unit) {
        networkState.postValue(NetworkState.RUNNING)
        scope.launch(getJobErrorHandler() + supervisorJob) {
            delay(200)
            val response = repository.searchPeoples(AppConfig.REGION, query, page, false, null)
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
