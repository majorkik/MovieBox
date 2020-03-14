package com.majorik.moviebox.pagination.search

import com.majorik.data.repositories.SearchRepository
import com.majorik.domain.NetworkState
import com.majorik.domain.constants.AppConfig
import com.majorik.domain.tmdbModels.movie.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchMovieDataSource(
    private val repository: SearchRepository,
    private val query: String,
    private val scope: CoroutineScope
) : BaseSearchDataSource<Movie>(repository, query, scope) {
    override fun executeQuery(page: Int, callback: (List<Movie>) -> Unit) {
        networkState.postValue(NetworkState.RUNNING)
        scope.launch(getJobErrorHandler() + supervisorJob) {
            delay(200)
            val response = repository.searchMovies(AppConfig.REGION, query, page, false, null, null, null)
            retryQuery = null
            networkState.postValue(NetworkState.SUCCESS)
            response?.results?.let { callback(it) }
        }
    }
}
