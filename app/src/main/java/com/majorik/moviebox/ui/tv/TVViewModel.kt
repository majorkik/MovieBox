package com.majorik.moviebox.ui.tv

import androidx.lifecycle.MutableLiveData
import com.majorik.data.repositories.TVRepository
import com.majorik.domain.models.CollectionResponse
import com.majorik.moviebox.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class TVViewModel(private val tvRepository: TVRepository) : BaseViewModel() {
    val popularTVsLiveData = MutableLiveData<MutableList<CollectionResponse.CollectionItem>>()
    val topRatedTVsLiveData = MutableLiveData<MutableList<CollectionResponse.CollectionItem>>()


    fun fetchPopularTVs(
        language: String?,
        page: Int?
    ) {
        ioScope.launch {
            val popularMovies = tvRepository.getPopularTVs(language, page)
            popularTVsLiveData.postValue(popularMovies)
        }
    }

    fun fetchTopRatedTVs(
        language: String?,
        page: Int?
    ) {
        ioScope.launch {
            val topRatedMovies = tvRepository.getTopRatedTVs(language, page)
            topRatedTVsLiveData.postValue(topRatedMovies)
        }
    }
}