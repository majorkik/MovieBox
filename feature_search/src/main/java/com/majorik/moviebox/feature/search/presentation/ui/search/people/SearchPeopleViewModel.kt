package com.majorik.moviebox.feature.search.presentation.ui.search.people

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.majorik.moviebox.feature.search.data.repositories.SearchRepository
import com.majorik.moviebox.feature.search.presentation.ui.search.datasource.SearchPersonPagingDataSource
import kotlinx.coroutines.flow.MutableStateFlow

internal class SearchPeopleViewModel(repository: SearchRepository) : ViewModel() {

    val query = MutableStateFlow("")

    var searchPeoplesDataSource: SearchPersonPagingDataSource? = null

    var searchPeoplesFlow = Pager(PagingConfig(pageSize = 20)) {
        SearchPersonPagingDataSource(query.value, repository).also {
            searchPeoplesDataSource = it
        }
    }.flow.cachedIn(viewModelScope)
}
