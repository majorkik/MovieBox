package com.majorik.moviebox.feature.details.data.repositories

import com.majorik.library.base.base.BaseRepository
import com.majorik.library.base.models.results.ResultWrapper
import com.majorik.moviebox.feature.details.data.api.DetailsRetrofitService
import com.majorik.moviebox.feature.details.domain.tmdbModels.person.PersonDetails
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class PersonRepository(private val api: DetailsRetrofitService) : BaseRepository() {
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    suspend fun getPersonById(
        personId: Int,
        language: String?,
        appendToResponse: String?
    ): ResultWrapper<PersonDetails> {
        return safeApiCall(dispatcher) {
            api.getPersonById(personId, language, appendToResponse)
        }
    }
}
