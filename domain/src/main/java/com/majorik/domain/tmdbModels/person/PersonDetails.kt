package com.majorik.domain.tmdbModels.person

import com.majorik.domain.tmdbModels.image.ImageDetails
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

    //append to response
    @field:Json(name = "movie_credits") val movieCredits: MovieCredits,
    @field:Json(name = "tv_credits") val tvCredits: TVCredits,
    @field:Json(name = "images") val images: Images,
    @field:Json(name = "tagged_images") val backdrops: TaggedImages
) {
    data class MovieCredits(
        @field:Json(name = "id") val id: Int,
        @field:Json(name = "cast") val cast: List<MovieCast>,
        @field:Json(name = "crew") val crew: List<MovieCrew>
    ) {
        data class MovieCast(
            @field:Json(name = "adult") val adult: Boolean,
            @field:Json(name = "backdrop_path") val backdropPath: String?,
            @field:Json(name = "character") val character: String,
            @field:Json(name = "credit_id") val creditId: String,
            @field:Json(name = "genre_ids") val genreIds: List<Int>,
            @field:Json(name = "id") val id: Int,
            @field:Json(name = "original_language") val originalLanguage: String,
            @field:Json(name = "original_title") val originalTitle: String,
            @field:Json(name = "overview") val overview: String,
            @field:Json(name = "popularity") val popularity: Double,
            @field:Json(name = "poster_path") val posterPath: String?,
            @field:Json(name = "release_date") val releaseDate: String,
            @field:Json(name = "title") val title: String,
            @field:Json(name = "video") val video: Boolean,
            @field:Json(name = "vote_average") val voteAverage: Double,
            @field:Json(name = "vote_count") val voteCount: Int
        )

        data class MovieCrew(
            @field:Json(name = "adult") val adult: Boolean,
            @field:Json(name = "backdrop_path") val backdropPath: String,
            @field:Json(name = "credit_id") val creditId: String,
            @field:Json(name = "department") val department: String,
            @field:Json(name = "genre_ids") val genreIds: List<Int>,
            @field:Json(name = "id") val id: Int,
            @field:Json(name = "job") val job: String,
            @field:Json(name = "original_language") val originalLanguage: String,
            @field:Json(name = "original_title") val originalTitle: String,
            @field:Json(name = "overview") val overview: String,
            @field:Json(name = "popularity") val popularity: Double,
            @field:Json(name = "poster_path") val posterPath: String?,
            @field:Json(name = "release_date") val releaseDate: String,
            @field:Json(name = "title") val title: String,
            @field:Json(name = "video") val video: Boolean,
            @field:Json(name = "vote_average") val voteAverage: Double,
            @field:Json(name = "vote_count") val voteCount: Int
        )
    }

    data class TVCredits(
        @field:Json(name = "id") val id: Int,
        @field:Json(name = "cast") val cast: List<TVCast>,
        @field:Json(name = "crew") val crew: List<TVCrew>
    ) {
        data class TVCast(
            @field:Json(name = "backdrop_path") val backdropPath: String?,
            @field:Json(name = "character") val character: String,
            @field:Json(name = "credit_id") val creditId: String,
            @field:Json(name = "episode_count") val episodeCount: Int,
            @field:Json(name = "first_air_date") val firstAirDate: String,
            @field:Json(name = "genre_ids") val genreIds: List<Int>,
            @field:Json(name = "id") val id: Int,
            @field:Json(name = "name") val name: String,
            @field:Json(name = "origin_country") val originCountry: List<String>,
            @field:Json(name = "original_language") val originalLanguage: String,
            @field:Json(name = "original_name") val originalName: String,
            @field:Json(name = "overview") val overview: String,
            @field:Json(name = "popularity") val popularity: Double,
            @field:Json(name = "poster_path") val posterPath: String?,
            @field:Json(name = "vote_average") val voteAverage: Double,
            @field:Json(name = "vote_count") val voteCount: Int
        )

        data class TVCrew(
            @field:Json(name = "backdrop_path") val backdropPath: String?,
            @field:Json(name = "credit_id") val creditId: String,
            @field:Json(name = "department") val department: String,
            @field:Json(name = "episode_count") val episodeCount: Int,
            @field:Json(name = "first_air_date") val firstAirDate: String,
            @field:Json(name = "genre_ids") val genreIds: List<Int>,
            @field:Json(name = "id") val id: Int,
            @field:Json(name = "job") val job: String,
            @field:Json(name = "name") val name: String,
            @field:Json(name = "origin_country") val originCountry: List<String>,
            @field:Json(name = "original_language") val originalLanguage: String,
            @field:Json(name = "original_name") val originalName: String,
            @field:Json(name = "overview") val overview: String,
            @field:Json(name = "popularity") val popularity: Double,
            @field:Json(name = "poster_path") val posterPath: String?,
            @field:Json(name = "vote_average") val voteAverage: Double,
            @field:Json(name = "vote_count") val voteCount: Int
        )
    }

    data class Images(
        @field:Json(name = "id") val id: Int,
        @field:Json(name = "profiles") val profiles: List<ImageDetails>
    )

    data class TaggedImages(
        @field:Json(name = "page") val page: Int,
        @field:Json(name = "total_results") val totalResults: Int,
        @field:Json(name = "total_pages") val totalPages: Int,
        @field:Json(name = "results") val results: List<ImageDetails>
    )
}