package com.majorik.moviebox.feature.collections.presentation.movieTabCollections

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.majorik.moviebox.feature.collections.data.repositories.MovieRepository
import com.majorik.moviebox.domain.enums.collections.MovieCollectionType
import com.majorik.moviebox.feature.collections.presentation.movieTabCollections.datasources.MovieCollectionsPagingDataSource

class MovieCollectionsViewModel(movieRepository: MovieRepository, movieCollectionType: MovieCollectionType) :
    ViewModel() {

    /**
     * Movies flow
     */

    var datasource: MovieCollectionsPagingDataSource? = null

    var moviesFlow = Pager(PagingConfig(pageSize = 20)) {
        MovieCollectionsPagingDataSource(movieRepository, movieCollectionType).also {
            datasource = it
        }
    }.flow.cachedIn(viewModelScope)
}
