package com.majorik.domain.tmdbModels.person

import com.squareup.moshi.Json

data class PersonResponse(
    @field:Json(name = "page") val page: Int,
    @field:Json(name = "total_results") val totalResults: Int,
    @field:Json(name = "total_pages") val totalPages: Int,
    @field:Json(name = "results") val results: List<Person>
){
    data class Person(
        @field:Json(name = "id") val id: Int,
        @field:Json(name = "profile_path") val profilePath: String?,
        @field:Json(name = "name") val name: String,
        @field:Json(name = "popularity") val popularity: Double
    )
}