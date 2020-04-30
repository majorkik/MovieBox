package com.majorik.moviebox.feature.collections.presentation.collections

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.majorik.moviebox.feature.collections.data.repositories.TVRepository
import com.majorik.moviebox.feature.collections.domain.movie.TVCollectionType
import com.majorik.moviebox.feature.collections.domain.tmdbModels.tv.TV
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
