package com.majorik.moviebox.pagination

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.majorik.data.repositories.SearchRepository
import com.majorik.domain.tmdbModels.search.MultiSearchResponse.MultiSearchItem
import kotlinx.coroutines.CoroutineScope

class SearchDataSourceFactory(
    private val repository: SearchRepository,
    private var query: String = "",
    private val scope: CoroutineScope
) : DataSource.Factory<Int, MultiSearchItem>() {
    val source = MutableLiveData<SearchDataSource>()

    override fun create(): DataSource<Int, MultiSearchItem> {
        val source = SearchDataSource(repository, query, scope)
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
