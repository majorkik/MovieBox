package com.majorik.moviebox.pagination.search

import com.majorik.data.repositories.SearchRepository
import com.majorik.domain.NetworkState
import com.majorik.domain.constants.AppConfig
import com.majorik.domain.tmdbModels.person.Person
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
            networkState.postValue(NetworkState.SUCCESS)
            response?.results?.let { callback(it) }
        }
    }
}
