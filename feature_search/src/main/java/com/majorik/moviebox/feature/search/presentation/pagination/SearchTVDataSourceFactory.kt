package com.majorik.moviebox.feature.search.presentation.pagination

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.majorik.moviebox.feature.search.data.repositories.SearchRepository
import com.majorik.moviebox.feature.search.domain.tmdbModels.tv.TV
import kotlinx.coroutines.CoroutineScope

class SearchTVDataSourceFactory(
    private val repository: SearchRepository,
    private var query: String = "",
    private val scope: CoroutineScope
) : DataSource.Factory<Int, TV>() {
    val source = MutableLiveData<SearchTVDataSource>()

    override fun create(): DataSource<Int, TV> {
        val source = SearchTVDataSource(
            repository,
            query,
            scope
        )
        this.source.postValue(source)
        return source
    }

    fun getQuery() = query

    fun getSource() = source.value

    fun updateQuery(query: String) {
        this.query = query
        getSource()?.refresh()
    }
}
