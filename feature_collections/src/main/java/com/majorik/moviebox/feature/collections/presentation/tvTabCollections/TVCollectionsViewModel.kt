package com.majorik.moviebox.feature.collections.presentation.tvTabCollections

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.majorik.library.base.constants.AppConfig
import com.majorik.moviebox.domain.enums.collections.TVCollectionType
import com.majorik.moviebox.feature.collections.data.repositories.TVRepository
import com.majorik.moviebox.feature.collections.presentation.tvTabCollections.datasources.TVCollectionsPagingDataSource

class TVCollectionsViewModel(tvRepository: TVRepository, tvCollectionType: TVCollectionType) :
    ViewModel() {

    /**
     * Movies flow
     */

    var datasource: TVCollectionsPagingDataSource? = null

    var tvsFlow = Pager(PagingConfig(pageSize = 20)) {
        TVCollectionsPagingDataSource(tvRepository, tvCollectionType, AppConfig.REGION).also {
            datasource = it
        }
    }.flow.cachedIn(viewModelScope)

}
