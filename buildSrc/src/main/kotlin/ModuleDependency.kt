import kotlin.reflect.full.memberProperties

private const val FEATURE_PREFIX = ":feature_"

@Suppress("unused")
object ModuleDependency {

    const val app = ":app"
    const val featureCollections = ":feature_collections"
    const val featureAuth = ":feature_auth"
    const val featureDetails = ":feature_details"
    const val featureSearch = ":feature_search"
    const val featureNavigation = ":feature_navigation"
    const val libraryBase = ":library_base"

    const val uiMovieDetails = ":ui_movie_details"
    const val uiTVDetails = ":ui_tv_details"
    const val uiNavMovies = ":ui_nav_movies"
    const val uiNavTvs = ":ui_nav_tvs"
    const val uiNavProfile = ":ui_nav_profile"
    const val uiNavSearch = ":ui_nav_search"
    const val uiCollection = ":ui_collection"
    const val uiDiscover = ":ui_discover"
    const val uiSearch = ":ui_search"
    const val uiAuth = ":ui_auth"

    const val coreBase = ":core_base"
    const val coreStrings = ":core_strings"
    const val coreUI = ":core_ui"
    const val coreNetwork = ":core_network"

    const val featureTmdbImpl = ":feature_tmdb_impl"
    const val featureTmdbApi = ":feature_tmdb_api"

    // False positive" function can be private"
    // See: https://youtrack.jetbrains.com/issue/KT-33610
    fun getAllModules() = ModuleDependency::class.memberProperties
        .filter { it.isConst }
        .map { it.getter.call().toString() }
        .toSet()

    fun getDynamicFeatureModules() = getAllModules()
        .filter { it.startsWith(FEATURE_PREFIX) }
        .toSet()
}
