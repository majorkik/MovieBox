package com.majorik.moviebox.feature.search.domain.enums

enum class SortFeature(val filtersQueryName: String) {
    /**
     * [ASCENDING] - по возрастанию
     * [DESCENDING] - по убыванию
     */
    ASCENDING("asc"),
    DESCENDING("desc")
}
