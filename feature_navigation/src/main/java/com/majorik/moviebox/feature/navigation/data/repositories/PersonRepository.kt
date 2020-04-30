package com.majorik.moviebox.feature.navigation.data.repositories

import com.majorik.moviebox.feature.navigation.data.api.TmdbApiService
import com.majorik.moviebox.feature.navigation.domain.tmdbModels.person.PersonResponse
import com.majorik.library.base.base.BaseRepository
import com.majorik.library.base.models.results.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class PersonRepository(private val api: TmdbApiService) : BaseRepository() {
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    suspend fun getPopularPeoples(language: String?, page: Int?): ResultWrapper<PersonResponse> {
        return safeApiCall(dispatcher) {
            api.getPopularPeoples(language, page)
        }
    }
}
