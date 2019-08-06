package com.majorik.moviebox.ui.tvDetails

import androidx.lifecycle.MutableLiveData
import com.majorik.data.repositories.TVRepository
import com.majorik.domain.tmdbModels.tv.TVDetails
import com.majorik.moviebox.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class TVDetailsViewModel(private val tvRepository: TVRepository) : BaseViewModel() {
    var tvDetailsLiveData = MutableLiveData<TVDetails>()

    fun fetchTVDetails(
        movieId: Int,
        language: String?,
        appendToResponse: String?,
        imageLanguages: String?
    ) {
        ioScope.launch {
            val movieDetails =
                tvRepository.getTVById(movieId, language, appendToResponse, imageLanguages)

            tvDetailsLiveData.postValue(movieDetails)
        }
    }
}