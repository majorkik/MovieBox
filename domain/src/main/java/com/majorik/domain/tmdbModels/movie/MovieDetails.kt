package com.majorik.domain.tmdbModels.movie

import com.majorik.domain.tmdbModels.*
import com.majorik.domain.tmdbModels.genre.Genre
import com.majorik.domain.tmdbModels.image.ImageDetails
import com.majorik.domain.tmdbModels.other.ProductionCompany
import com.majorik.domain.tmdbModels.other.ProductionCountry
import com.majorik.domain.tmdbModels.other.SpokenLanguage
import com.majorik.domain.tmdbModels.other.Video
import com.squareup.moshi.Json

data class MovieDetails(
    @field:Json(name = "adult") val adult: Boolean,
    @field:Json(name = "backdrop_path") val backdropPath: String?,
    @field:Json(name = "belongs_to_collection") val belongsToCollection: Collection?,
    @field:Json(name = "budget") val budget: Int,
    @field:Json(name = "genres") val genres: List<Genre>,
    @field:Json(name = "homepage") val homepage: String?,
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "imdb_id") val imdbId: String?,
    @field:Json(name = "original_language") val originalLanguage: String,
    @field:Json(name = "original_title") val originalTitle: String,
    @field:Json(name = "overview") val overview: String?,
    @field:Json(name = "popularity") val popularity: Double,
    @field:Json(name = "poster_path") val posterPath: String?,
    @field:Json(name = "production_companies") val productionCompanies: List<ProductionCompany>,
    @field:Json(name = "production_countries") val productionCountries: List<ProductionCountry>,
    @field:Json(name = "release_date") val releaseDate: String,
    @field:Json(name = "revenue") val revenue: Long,
    @field:Json(name = "runtime") val runtime: Int?,
    @field:Json(name = "spoken_languages") val spokenLanguages: List<SpokenLanguage>,
    @field:Json(name = "status") val status: String,
    @field:Json(name = "tagline") val tagline: String?,
    @field:Json(name = "title") val title: String,
    @field:Json(name = "video") val video: Boolean,
    @field:Json(name = "vote_average") val voteAverage: Double,
    @field:Json(name = "vote_count") val voteCount: Int,

    @field:Json(name = "images") val images: Images,
    @field:Json(name = "credits") val credits: Credits,
    @field:Json(name = "videos") val videos: Videos
) {
    data class Collection(
        @field:Json(name = "id") val id: Int,
        @field:Json(name = "name") val name: String,
        @field:Json(name = "poster_path") val posterPath: String?,
        @field:Json(name = "backdrop_path") val backdropPath: String?
    )

    //append to response
    data class Images(
        @field:Json(name = "id") val id: Int,
        @field:Json(name = "backdrops") val backdrops: List<ImageDetails>,
        @field:Json(name = "posters") val posters: List<ImageDetails>
    )

    data class Credits(
        @field:Json(name = "id") val id: Int,
        @field:Json(name = "cast") val casts: List<Cast>,
        @field:Json(name = "crew") val crews: List<Crew>
    )

    data class Videos(
        @field:Json(name = "results") val results: List<Video>
    )
}