package com.majorik.data.repositories

import com.majorik.data.api.TmdbApiService
import com.majorik.domain.models.image.ImageDetails
import com.majorik.domain.models.image.PersonImagesResponse
import com.majorik.domain.models.person.PersonDetails

class PersonRepository(private val api: TmdbApiService) : BaseRepository() {
    suspend fun getPersonById(
        personId: Int,
        language: String?,
        appendToResponse: String?
    ): PersonDetails? {
        return safeApiCall(
            call = {
                api.getPersonById(personId, language, appendToResponse).await()
            },
            errorMessage = "Ошибка при получении информации об актере"
        )
    }

    suspend fun getPersonTaggedImages(
        personId: Int,
        language: String?,
        page: Int?
    ): MutableList<ImageDetails>? {
        val personResponse = safeApiCall(
            call = {
                api.getPersonTaggedImages(personId, language, page).await()
            },
            errorMessage = "Ошибка при получении списка картинок с актером"
        )

        return personResponse?.results?.toMutableList()
    }

    suspend fun getPersonPosters(
        personId: Int
    ): MutableList<ImageDetails>?{
        val personResponse = safeApiCall(
            call = {
                api.getPersonPosters(personId).await()
            },
            errorMessage = "Ошибка при получении постеров актера"
        )

        return personResponse?.profiles?.toMutableList()
    }
}