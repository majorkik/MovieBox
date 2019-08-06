package com.majorik.domain.tmdbModels.image

import com.squareup.moshi.Json

data class ImageDetails(
    @field:Json(name = "aspect_ratio") val aspectRatio: Double,
    @field:Json(name = "file_path") val filePath: String,
    @field:Json(name = "height") val height: Int,
    @field:Json(name = "iso_631_1") val iso_631_1: String?,
    @field:Json(name = "vote_average") val voteAverage: Double,
    @field:Json(name = "vote_count") val voteCount: Int,
    @field:Json(name = "width") val width: Int
)