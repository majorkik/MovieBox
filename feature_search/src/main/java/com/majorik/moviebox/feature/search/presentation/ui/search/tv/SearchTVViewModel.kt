package com.majorik.moviebox.feature.search.presentation.ui.search.tv

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.majorik.moviebox.feature.search.data.repositories.SearchRepository
import com.majorik.moviebox.feature.search.presentation.ui.search.datasource.SearchTVPagingDataSource
import kotlinx.coroutines.flow.MutableStateFlow

internal class SearchTVViewModel(repository: SearchRepository) : ViewModel() {
    val query = MutableStateFlow("")

    var searchTVsDataSource: SearchTVPagingDataSource? = null

    var searchTVsFlow = Pager(PagingConfig(pageSize = 20)) {
        SearchTVPagingDataSource(query.value, repository).also {
            searchTVsDataSource = it
        }
    }.flow.cachedIn(viewModelScope)
}
