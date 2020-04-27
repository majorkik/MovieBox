package com.majorik.moviebox.feature.search.domain.tmdbModels.person

import com.majorik.moviebox.feature.search.domain.tmdbModels.cast.MovieCast
import com.majorik.moviebox.feature.search.domain.tmdbModels.cast.TVCast
import com.majorik.moviebox.feature.search.domain.tmdbModels.crew.MovieCrew
import com.majorik.moviebox.feature.search.domain.tmdbModels.crew.TVCrew
import com.majorik.moviebox.feature.search.domain.tmdbModels.image.ImagesResponse
import com.majorik.moviebox.feature.search.domain.tmdbModels.image.ProfileImages
import com.squareup.moshi.Json

data class PersonDetails(
    @field:Json(name = "birthday") val birthday: String?,
    @field:Json(name = "known_for_department") val knownForDepartment: String,
    @field:Json(name = "deathday") val deathday: String?,
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "also_known_as") val alsoKnownAs: List<String>,
    @field:Json(name = "gender") val gender: Int,
    @field:Json(name = "biography") val biography: String,
    @field:Json(name = "popularity") val popularity: Double,
    @field:Json(name = "place_of_birth") val placeOfBirth: String?,
    @field:Json(name = "profile_path") val profilePath: String?,
    @field:Json(name = "adult") val adult: Boolean,
    @field:Json(name = "imdb_id") val imdbId: String,
    @field:Json(name = "homepage") val homepage: String?,

    // append to response
    @field:Json(name = "movie_credits") val movieCredits: MovieCredits,
    @field:Json(name = "tv_credits") val tvCredits: TVCredits,
    @field:Json(name = "images") val profileImages: ProfileImages,
    @field:Json(name = "tagged_images") val backdrops: ImagesResponse
) {
    data class MovieCredits(
        @field:Json(name = "id") val id: Int,
        @field:Json(name = "cast") val cast: List<MovieCast>,
        @field:Json(name = "crew") val crew: List<MovieCrew>
    )

    data class TVCredits(
        @field:Json(name = "id") val id: Int,
        @field:Json(name = "cast") val cast: List<TVCast>,
        @field:Json(name = "crew") val crew: List<TVCrew>
    )
}
