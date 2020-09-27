package com.majorik.moviebox.feature.search.domain.constants

object DiscoverFilters {
    const val SORT_BY = "sort_by"
    const val WITH_GENRES = "with_genres"
    const val WITH_KEYWORDS = "with_keywords"
    const val WITH_PEOPLE = "with_people"
    const val VOTE_AVERAGE = "vote_average"
    const val RELEASE_DATE = "release_date"
    const val YEAR = "year"
    const val FIRST_AIR_DATE_YEAR = "first_air_date_year"
    const val INCLUDE_ADULT = "include_adult"
    const val AIR_DATE = "first_air_date"

    /**
     * [TYPE_LTE] - меньше или равно (максимум)
     * [TYPE_GTE] - больше или равно (минимум)
     */
    const val TYPE_LTE = "lte"
    const val TYPE_GTE = "gte"

    const val INCLUDE_NULL_FIRST_AIR_DATES = "include_null_first_air_dates"
}
