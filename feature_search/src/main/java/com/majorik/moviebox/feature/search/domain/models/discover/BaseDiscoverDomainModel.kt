package com.majorik.moviebox.feature.search.domain.models.discover

import com.majorik.moviebox.feature.search.domain.enums.DiscoverType
import com.majorik.moviebox.feature.search.domain.tmdbModels.movie.Movie
import com.majorik.moviebox.feature.search.domain.tmdbModels.tv.TV
import com.soywiz.klock.DateFormat
import com.soywiz.klock.DateTimeTz

data class BaseDiscoverDomainModel(
    val id: Int,
    val type: DiscoverType,
    val name: String,
    val genres: List<Int>?,
    val posterImage: String?,
    val backdropImage: String?,
    val releaseDate: DateTimeTz?,
    val voteAverage: Double
)

fun Movie.toDiscoverDomainModel() = BaseDiscoverDomainModel(
    id = id,
    type = DiscoverType.MOVIES,
    name = title,
    genres = genreIds,
    posterImage = posterPath,
    backdropImage = backdropPath,
    releaseDate = releaseDate?.let { DateFormat("yyyy-MM-dd").tryParse(it) },
    voteAverage = voteAverage
)

fun TV.toDiscoverDomainModel() = BaseDiscoverDomainModel(
    id = id,
    type = DiscoverType.TVS,
    name = name,
    genres = genreIds,
    posterImage = posterPath,
    backdropImage = backdropPath,
    releaseDate = firstAirDate?.let { DateFormat("yyyy-MM-dd").tryParse(it) },
    voteAverage = voteAverage
)
