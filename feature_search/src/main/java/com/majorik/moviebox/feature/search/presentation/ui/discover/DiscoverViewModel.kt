package com.majorik.moviebox.feature.search.presentation.ui.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.majorik.library.base.constants.AppConfig
import com.majorik.moviebox.feature.search.data.repositories.DiscoverRepository
import com.majorik.moviebox.feature.search.domain.models.discover.DiscoverFiltersModel
import com.majorik.moviebox.feature.search.presentation.ui.discover.datasources.DiscoverPagingDataSource

class DiscoverViewModel(private val repository: DiscoverRepository) : ViewModel() {

    var filtersModel: DiscoverFiltersModel = DiscoverFiltersModel()

    var discoverDataSource: DiscoverPagingDataSource? = null

    private var language: String? = AppConfig.REGION

    val discoverFlow = Pager(PagingConfig(pageSize = 20)) {
        DiscoverPagingDataSource(
            repository = repository,
            language = language,
            filtersModel = filtersModel
        ).also {
            discoverDataSource = it
        }
    }.flow.cachedIn(viewModelScope)
}
