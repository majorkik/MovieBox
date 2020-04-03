package com.majorik.feature.search.domain.tmdbModels.tv

import com.majorik.feature.search.domain.tmdbModels.credits.Credits
import com.majorik.feature.search.domain.tmdbModels.genre.Genre
import com.majorik.feature.search.domain.tmdbModels.image.Images
import com.majorik.feature.search.domain.tmdbModels.production.ProductionCompany
import com.majorik.feature.search.domain.tmdbModels.video.Videos
import com.squareup.moshi.Json

data class TVDetails(
    @field:Json(name = "backdrop_path") val backdropPath: String?,
    @field:Json(name = "created_by") val createdBy: List<CreatedBy>,
    @field:Json(name = "episode_run_time") val episodeRunTime: List<Int>,
    @field:Json(name = "first_air_date") val firstAirDate: String,
    @field:Json(name = "genres") val genres: List<Genre>,
    @field:Json(name = "homepage") val homepage: String,
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "in_production") val inProduction: Boolean,
    @field:Json(name = "languages") val languages: List<String>,
    @field:Json(name = "last_air_date") val lastAirDate: String,
    @field:Json(name = "last_episode_to_air") val episodeToAir: EpisodeToAir,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "networks") val networks: List<Network>,
    @field:Json(name = "next_episode_to_air") val nextEpisodeToAir: EpisodeToAir,
    @field:Json(name = "number_of_episodes") val numberOfEpisodes: Int,
    @field:Json(name = "number_of_seasons") val numberOfSeasons: Int,
    @field:Json(name = "origin_country") val originCountry: List<String>,
    @field:Json(name = "original_language") val originalLanguage: String,
    @field:Json(name = "original_name") val originalName: String,
    @field:Json(name = "overview") val overview: String,
    @field:Json(name = "popularity") val popularity: Double,
    @field:Json(name = "poster_path") val posterPath: String?,
    @field:Json(name = "production_companies") val productionCompanies: List<ProductionCompany>,
    @field:Json(name = "seasons") val seasons: List<Season>,
    @field:Json(name = "status") val status: String,
    @field:Json(name = "type") val type: String,
    @field:Json(name = "vote_average") val voteAverage: Double,
    @field:Json(name = "vote_count") val voteCount: Int,

    // append to response
    @field:Json(name = "images") val images: Images,
    @field:Json(name = "credits") val credits: Credits,
    @field:Json(name = "videos") val videos: Videos
) {
    data class CreatedBy(
        @field:Json(name = "id") val id: Int,
        @field:Json(name = "credit_id") val creditId: String,
        @field:Json(name = "name") val name: String,
        @field:Json(name = "gender") val gender: Int,
        @field:Json(name = "profile_path") val profilePath: String
    )

    data class EpisodeToAir(
        @field:Json(name = "air_date") val airDate: String,
        @field:Json(name = "episode_number") val episodeNumber: Int,
        @field:Json(name = "id") val id: Int,
        @field:Json(name = "name") val name: String,
        @field:Json(name = "overview") val overview: String,
        @field:Json(name = "production_code") val productionCode: String,
        @field:Json(name = "season_number") val seasonNumber: Int,
        @field:Json(name = "show_id") val showId: Int,
        @field:Json(name = "still_path") val stillPath: String,
        @field:Json(name = "vote_average") val voteAverage: Double,
        @field:Json(name = "vote_count") val voteCount: Int
    )

    data class Network(
        @field:Json(name = "name") val name: String,
        @field:Json(name = "id") val id: Int,
        @field:Json(name = "logo_path") val logoPath: String,
        @field:Json(name = "origin_country") val originCountry: String
    )

    data class Season(
        @field:Json(name = "air_date") val airDate: String,
        @field:Json(name = "episode_count") val episodeCount: Int,
        @field:Json(name = "id") val id: Int,
        @field:Json(name = "name") val name: String,
        @field:Json(name = "overview") val overview: String,
        @field:Json(name = "poster_path") val posterPath: String,
        @field:Json(name = "season_number") val seasonNumber: Int
    )
}
