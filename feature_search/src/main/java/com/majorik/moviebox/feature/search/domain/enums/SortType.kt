package com.majorik.moviebox.feature.search.domain.enums

enum class SortType(val filterQueryName: String) {
    BY_POPULARITY("popularity"),

    BY_DATE("release_date"),

    BY_VOTE_AVERAGE("vote_average"),

    BY_VOTE_COUNT("vote_count")
}
