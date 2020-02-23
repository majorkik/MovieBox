package com.majorik.data.repositories

import com.majorik.data.api.TmdbApiService
import com.majorik.domain.tmdbModels.image.ImageDetails
import com.majorik.domain.tmdbModels.person.Person
import com.majorik.domain.tmdbModels.person.PersonDetails

class PersonRepository(private val api: TmdbApiService) : BaseRepository() {
    suspend fun getPersonById(
        personId: Int,
        language: String?,
        appendToResponse: String?
    ): PersonDetails? {
        return safeApiCall(
            call = {
                api.getPersonById(personId, language, appendToResponse)
            },
            errorMessage = "Ошибка GET[getPersonById] (personId = $personId)"
        )
    }

    suspend fun getPersonTaggedImages(
        personId: Int,
        language: String?,
        page: Int?
    ): MutableList<ImageDetails>? {
        val personResponse = safeApiCall(
            call = {
                api.getPersonTaggedImages(personId, language, page)
            },
            errorMessage = "Ошибка GET[getPersonTaggedImages] (personId = $personId)"
        )

        return personResponse?.results?.toMutableList()
    }

    suspend fun getPersonPosters(
        personId: Int
    ): MutableList<ImageDetails>? {
        val response = safeApiCall(
            call = {
                api.getPersonPosters(personId)
            },
            errorMessage = "Ошибка GET[getPersonPosters] (personId = $personId)"
        )

        return response?.profiles?.toMutableList()
    }

    suspend fun getPopularPeoples(language: String?, page: Int?): MutableList<Person>? {
        val response = safeApiCall(
            call = {
                api.getPopularPeoples(language, page)
            },
            errorMessage = "Ошибка GET[getPopularPeoples]"
        )

        return response?.results?.toMutableList()
    }

    suspend fun getPersonMovieCredits(personId: Int, language: String?): PersonDetails.MovieCredits? {
        return safeApiCall(
            call = {
                api.getMovieCredits(personId, language)
            },
            errorMessage = "Ошибка GET[getPersonMovieCredits]"
        )
    }

    suspend fun getPersonTVCredits(personId: Int, language: String?): PersonDetails.TVCredits? {
        return safeApiCall(
            call = {
                api.getTVCredits(personId, language)
            },
            errorMessage = "Ошибка GET[getPersonTVCredits]"
        )
    }
}
