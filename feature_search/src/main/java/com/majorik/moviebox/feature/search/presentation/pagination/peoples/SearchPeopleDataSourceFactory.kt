package com.majorik.moviebox.feature.search.presentation.pagination.peoples

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.majorik.moviebox.feature.search.data.repositories.SearchRepository
import com.majorik.moviebox.feature.search.domain.tmdbModels.person.Person
import kotlinx.coroutines.CoroutineScope

internal class SearchPeopleDataSourceFactory(
    private val repository: SearchRepository,
    private var query: String = "",
    private val scope: CoroutineScope
) : DataSource.Factory<Int, Person>() {
    val source = MutableLiveData<SearchPeopleDataSource>()

    override fun create(): DataSource<Int, Person> {
        val source =
            SearchPeopleDataSource(
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
