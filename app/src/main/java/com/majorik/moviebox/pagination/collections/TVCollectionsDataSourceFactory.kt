package com.majorik.moviebox.pagination.collections

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.majorik.data.repositories.TVRepository
import com.majorik.domain.enums.movie.TVCollectionType
import com.majorik.domain.tmdbModels.tv.TV
import com.majorik.moviebox.pagination.collections.TVCollectionsDataSource
import kotlinx.coroutines.CoroutineScope

class TVCollectionsDataSourceFactory(
    private val tvRepository: TVRepository,
    private val scope: CoroutineScope,
    private val tvCollectionType: TVCollectionType
) : DataSource.Factory<Int, TV>() {
    val source = MutableLiveData<TVCollectionsDataSource>()

    override fun create(): DataSource<Int, TV> {
        val source =
            TVCollectionsDataSource(
                tvRepository,
                scope,
                tvCollectionType
            )
        this.source.postValue(source)
        return source
    }

    fun getSource() = source.value

    fun refresh() {
        getSource()?.refresh()
    }
}
