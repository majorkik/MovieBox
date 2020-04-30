package com.majorik.moviebox.feature.collections.presentation.tvTabCollections

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.majorik.moviebox.feature.collections.data.repositories.TVRepository
import com.majorik.moviebox.feature.collections.domain.NetworkState
import com.majorik.moviebox.feature.collections.domain.movie.TVCollectionType
import com.majorik.moviebox.feature.collections.presentation.collections.TVCollectionsDataSourceFactory

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
