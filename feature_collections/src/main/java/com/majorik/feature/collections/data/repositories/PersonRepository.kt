package com.majorik.feature.collections.data.repositories

import com.majorik.feature.collections.data.api.TmdbApiService
import com.majorik.feature.collections.domain.tmdbModels.image.ImagesResponse
import com.majorik.feature.collections.domain.tmdbModels.image.PersonPostersResponse
import com.majorik.feature.collections.domain.tmdbModels.person.PersonDetails
import com.majorik.feature.collections.domain.tmdbModels.person.PersonResponse
import com.majorik.library.base.base.BaseRepository
import com.majorik.library.base.models.results.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class PersonRepository(private val api: TmdbApiService) : BaseRepository() {
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

    suspend fun getPersonTaggedImages(
        personId: Int,
        language: String?,
        page: Int?
    ): ResultWrapper<ImagesResponse> {
        return safeApiCall(dispatcher) {
            api.getPersonTaggedImages(personId, language, page)
        }
    }

    suspend fun getPersonPosters(
        personId: Int
    ): ResultWrapper<PersonPostersResponse> {
        return safeApiCall(dispatcher) {
            api.getPersonPosters(personId)
        }
    }

    suspend fun getPopularPeoples(language: String?, page: Int?): ResultWrapper<PersonResponse> {
        return safeApiCall(dispatcher) {
            api.getPopularPeoples(language, page)
        }
    }

    suspend fun getPersonMovieCredits(
        personId: Int,
        language: String?
    ): ResultWrapper<PersonDetails.MovieCredits> {
        return safeApiCall(dispatcher) {
            api.getMovieCredits(personId, language)
        }
    }

    suspend fun getPersonTVCredits(
        personId: Int,
        language: String?
    ): ResultWrapper<PersonDetails.TVCredits> {
        return safeApiCall(dispatcher) {
            api.getTVCredits(personId, language)
        }
    }
}
