package com.majorik.moviebox.feature.details.presentation.person_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.majorik.library.base.models.results.ResultWrapper
import com.majorik.moviebox.feature.details.data.repositories.PersonRepository
import com.majorik.moviebox.feature.details.domain.tmdbModels.person.PersonDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PersonDetailsViewModel(private val personRepository: PersonRepository) : ViewModel() {

    /**
     * Person details
     */

    var personDetailsFlow: MutableStateFlow<ResultWrapper<PersonDetails>?> = MutableStateFlow(null)

    fun fetchPersonDetails(
        personId: Int,
        language: String?,
        appendToResponse: String?
    ) {
        viewModelScope.launch {
            val response = personRepository.getPersonById(personId, language, appendToResponse)

            personDetailsFlow.value = response
        }
    }
}
