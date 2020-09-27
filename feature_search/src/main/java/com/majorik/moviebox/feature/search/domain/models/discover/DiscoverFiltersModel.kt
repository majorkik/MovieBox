package com.majorik.moviebox.feature.search.domain.models.discover

import com.majorik.moviebox.feature.search.domain.constants.DiscoverFilters.AIR_DATE
import com.majorik.moviebox.feature.search.domain.constants.DiscoverFilters.FIRST_AIR_DATE_YEAR
import com.majorik.moviebox.feature.search.domain.constants.DiscoverFilters.INCLUDE_ADULT
import com.majorik.moviebox.feature.search.domain.constants.DiscoverFilters.INCLUDE_NULL_FIRST_AIR_DATES
import com.majorik.moviebox.feature.search.domain.constants.DiscoverFilters.RELEASE_DATE
import com.majorik.moviebox.feature.search.domain.constants.DiscoverFilters.SORT_BY
import com.majorik.moviebox.feature.search.domain.constants.DiscoverFilters.TYPE_GTE
import com.majorik.moviebox.feature.search.domain.constants.DiscoverFilters.TYPE_LTE
import com.majorik.moviebox.feature.search.domain.constants.DiscoverFilters.VOTE_AVERAGE
import com.majorik.moviebox.feature.search.domain.constants.DiscoverFilters.WITH_GENRES
import com.majorik.moviebox.feature.search.domain.constants.DiscoverFilters.WITH_KEYWORDS
import com.majorik.moviebox.feature.search.domain.constants.DiscoverFilters.WITH_PEOPLE
import com.majorik.moviebox.feature.search.domain.constants.DiscoverFilters.YEAR
import com.majorik.moviebox.feature.search.domain.enums.DiscoverType
import com.majorik.moviebox.feature.search.domain.enums.SortFeature
import com.majorik.moviebox.feature.search.domain.enums.SortType
import java.io.Serializable
import java.text.DecimalFormat

data class DiscoverFiltersModel(

    /**
     * [discoverType] Тип элементов в поиске (Фильмы или сериалы)
     */

    var discoverType: DiscoverType = DiscoverType.MOVIES,

    /**
     * Тип сортировки: [sortType]
     * - по дате, по полулярности, по количеству оценок, по средней оценке
     */
    var sortType: SortType = SortType.BY_POPULARITY,

    /**
     * Особенность сортировки: [sortFeature]
     * - по убыванию, по возрастанию
     */
    var sortFeature: SortFeature = SortFeature.DESCENDING,

    /**
     *  Фильтр по годам, доступно три варианта использования:
     *  - любой год - оба поля должны быть null
     *  - по году - используем только [minYear]
     *  - между годами - используем оба поля ([minYear] и [maxYear])
     */
    var minYear: Int? = null,
    var maxYear: Int? = null,

    /**
     * Фильтр по средней оценке ([voteAverageMin] и [voteAverageMax]
     */
    var voteAverageMin: Float? = null,
    var voteAverageMax: Float? = null,

    /**
     * Фильтр по жанрам [movieGenresIds]
     */
    var movieGenresIds: List<Int>? = null,

    /**
     * Фильтр по жанрам [tvGenresIds]
     */
    var tvGenresIds: List<Int>? = null,

    /**
     * Фильтр по ключевым словам [keywordsIds]
     */
    var keywordsIds: List<Int>? = null,

    /**
     * Фильтр по актерам [creditsIds] (только для фильмов) !!!
     */
    var creditsIds: List<Int>? = null,

    /**
     * Влючать в контент для взрослых [includeAdult] (только для фильмов) !!!
     */
    var includeAdult: Boolean? = null,

    /**
     * Показать только те сериалы, которые еще не вышли [includeTVsWithNullAirDate] (только для сериалов) !!!
     */
    var includeTVsWithNullAirDate: Boolean? = null
) : Serializable {
    /**
     * Метод для восстановления значений по-умолчанию [reset]
     */
    fun reset() {
        sortType = SortType.BY_POPULARITY
        sortFeature = SortFeature.DESCENDING

        minYear = null
        maxYear = null

        voteAverageMin = null
        voteAverageMax = null

        movieGenresIds = null
        tvGenresIds = null
        keywordsIds = null
        creditsIds = null

        includeAdult = null

        includeTVsWithNullAirDate = null
    }
}

fun DiscoverFiltersModel.getFiltersMapForMovies(): Map<String, String> {
    val decimalFormat = DecimalFormat("##.#")
    val mapFilters: MutableMap<String, String> = mutableMapOf()

    mapFilters[SORT_BY] = "${sortType.filterQueryName}.${sortFeature.filtersQueryName}"

    includeAdult?.let { mapFilters[INCLUDE_ADULT] = it.toString() }

    voteAverageMin?.let { mapFilters["$VOTE_AVERAGE.$TYPE_GTE"] = decimalFormat.format(it / 10).toString() }
    voteAverageMax?.let { mapFilters["$VOTE_AVERAGE.$TYPE_LTE"] = decimalFormat.format(it / 10).toString() }

    if (minYear != null && maxYear == null) {
        minYear?.let { mapFilters[YEAR] = it.toString() }
    } else {
        minYear?.let { mapFilters["$RELEASE_DATE.$TYPE_GTE"] = "$it-01-01" }
        maxYear?.let { mapFilters["$RELEASE_DATE.$TYPE_LTE"] = "$it-12-31" }
    }

    movieGenresIds?.let { mapFilters[WITH_GENRES] = it.joinToString("|") { id -> id.toString() } }

    creditsIds?.let { mapFilters[WITH_PEOPLE] = it.joinToString(",") { id -> id.toString() } }

    keywordsIds?.let { mapFilters[WITH_KEYWORDS] = it.joinToString(",") { id -> id.toString() } }

    return mapFilters
}

fun DiscoverFiltersModel.getFiltersMapForTVs(): Map<String, String> {
    val decimalFormat = DecimalFormat("##.#")
    val mapFilters: MutableMap<String, String> = mutableMapOf()

    mapFilters[SORT_BY] = "${sortType.filterQueryName}.${sortFeature.filtersQueryName}"

    voteAverageMin?.let { mapFilters["$VOTE_AVERAGE.$TYPE_GTE"] = decimalFormat.format(it / 10).toString() }
    voteAverageMax?.let { mapFilters["$VOTE_AVERAGE.$TYPE_LTE"] = decimalFormat.format(it / 10).toString() }

    tvGenresIds?.let { mapFilters[WITH_GENRES] = it.joinToString("|") { id -> id.toString() } }

    keywordsIds?.let { mapFilters[WITH_KEYWORDS] = it.joinToString(",") { id -> id.toString() } }

    if (minYear != null && maxYear == null) {
        minYear?.let { mapFilters[FIRST_AIR_DATE_YEAR] = it.toString() }
    } else {
        minYear?.let { mapFilters["$AIR_DATE.$TYPE_GTE"] = "$it-01-01"  }
        maxYear?.let { mapFilters["$AIR_DATE.$TYPE_LTE"] = "$it-12-31" }
    }

    includeTVsWithNullAirDate?.let {
        mapFilters[INCLUDE_NULL_FIRST_AIR_DATES] = it.toString()
    }

    return mapFilters
}
