package com.majorik.moviebox.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations.switchMap
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.majorik.data.repositories.SearchRepository
import com.majorik.domain.NetworkState
import com.majorik.moviebox.pagination.SearchDataSourceFactory
import com.majorik.moviebox.ui.base.BaseViewModel

class SearchableViewModel(
    repository: SearchRepository
) : BaseViewModel() {

    private val searchDataSource = SearchDataSourceFactory(
        repository = repository,
        scope = ioScope
    )

    val searchResults = LivePagedListBuilder(searchDataSource, pagedConfig()).build()

    val networkState: LiveData<NetworkState>? =
        switchMap(searchDataSource.source) { it.getNetworkState() }

    fun fetchItems(query: String) {
        val search = query.trim()
        if (searchDataSource.getQuery() == search) return
        searchDataSource.updateQuery(search)
    }

    private fun pagedConfig() = PagedList.Config.Builder()
        .setInitialLoadSizeHint(5)
        .setEnablePlaceholders(false)
        .setPageSize(20)
        .build()

    fun refreshFailedRequest() = searchDataSource.getSource()?.retryFailedQuery()

    fun refreshAllList() = searchDataSource.getSource()?.refresh()
}
