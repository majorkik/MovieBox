package com.majorik.moviebox.ui.tvTabCollections

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.majorik.data.repositories.TVRepository
import com.majorik.domain.NetworkState
import com.majorik.domain.enums.movie.TVCollectionType
import com.majorik.moviebox.pagination.collections.TVCollectionsDataSourceFactory

class TVCollectionsViewModel(tvRepository: TVRepository, tvCollectionType: TVCollectionType) :
    ViewModel() {
    private val dataSourceFactory =
        TVCollectionsDataSourceFactory(
            tvRepository = tvRepository,
            scope = viewModelScope,
            tvCollectionType = tvCollectionType
        )

    val tvResults = LivePagedListBuilder(dataSourceFactory, pagedConfig()).build()

    val networkState: LiveData<NetworkState>? =
        Transformations.switchMap(dataSourceFactory.source) { it.getNetworkState() }

    fun fetchItems() {
        dataSourceFactory.refresh()
    }

    private fun pagedConfig() = PagedList.Config.Builder()
        .setInitialLoadSizeHint(5)
        .setEnablePlaceholders(false)
        .setPageSize(20)
        .build()

    fun refreshFailedRequest() = dataSourceFactory.getSource()?.retryFailedQuery()

    fun refreshAllList() = dataSourceFactory.getSource()?.refresh()
}
