package com.majorik.feature.search.domain.traktModels.sync

import com.squareup.moshi.Json

data class TraktLastActivityResponse(
    @field:Json(name = "all") val allData: String,
    @field:Json(name = "movies") val moviesActivity: MoviesActivity,
    @field:Json(name = "episodes") val episodesActivity: EpisodesActivity,
    @field:Json(name = "seasons") val seasonsActivity: SeasonsActivity,
    @field:Json(name = "shows") val showsActivity: ShowsActivity,
    @field:Json(name = "comments") val commentsActivity: CommentsActivity,
    @field:Json(name = "lists") val listsActivity: ListActivity
) {
    class MoviesActivity(
        @field:Json(name = "watched_at") val watchedAt: String,
        @field:Json(name = "collected_at") val collectedAt: String,
        @field:Json(name = "rated_at") val ratedAt: String,
        @field:Json(name = "watchlisted_at") val watchlistedAt: String,
        @field:Json(name = "commented_at") val commentedAt: String,
        @field:Json(name = "paused_at") val pausedAt: String,
        @field:Json(name = "hidden_at") val hiddenAt: String
    )

    class EpisodesActivity(
        @field:Json(name = "watched_at") val watchedAt: String,
        @field:Json(name = "collected_at") val collectedAt: String,
        @field:Json(name = "rated_at") val ratedAt: String,
        @field:Json(name = "watchlisted_at") val watchlistedAt: String,
        @field:Json(name = "commented_at") val commentedAt: String,
        @field:Json(name = "paused_at") val pausedAt: String
    )

    class ShowsActivity(
        @field:Json(name = "rated_at") val ratedAt: String,
        @field:Json(name = "watchlisted_at") val watchlistedAt: String,
        @field:Json(name = "commented_at") val commentedAt: String,
        @field:Json(name = "hidden_at") val hiddenAt: String
    )

    class SeasonsActivity(
        @field:Json(name = "rated_at") val ratedAt: String,
        @field:Json(name = "watchlisted_at") val watchlistedAt: String,
        @field:Json(name = "commented_at") val commentedAt: String,
        @field:Json(name = "hidden_at") val hiddenAt: String
    )

    class CommentsActivity(
        @field:Json(name = "liked_at") val likedAt: String
    )

    class ListActivity(
        @field:Json(name = "liked_at") val likedAt: String,
        @field:Json(name = "updated_at") val updatedAt: String,
        @field:Json(name = "commented_at") val commentedAt: String
    )
}
