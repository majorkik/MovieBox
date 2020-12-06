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
