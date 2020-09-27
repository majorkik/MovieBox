package com.majorik.moviebox.feature.search.domain.enums

enum class DiscoverType {
    MOVIES, TVS
}

fun DiscoverType.isMoviesType() = this == DiscoverType.MOVIES

fun DiscoverType.isTVsType() = this == DiscoverType.TVS

fun DiscoverType.isMoviesTypeWithAction(action: () -> Unit) {
    if (isMoviesType()) action()
}

fun DiscoverType.isTVsTypeWithAction(action: () -> Unit) {
    if (isTVsType()) action()
}
