import kotlin.reflect.full.memberProperties

private const val FEATURE_PREFIX = ":feature_"

// "Module" means "project" in terminology of Gradle API. To be specific each "Android module" is a Gradle "subproject"
@Suppress("unused")
object ModuleDependency {
    // All consts are accessed via reflection
    const val APP = ":app"
    const val FEATURE_COLLECTIONS = ":feature_collections"
    const val FEATURE_AUTH = ":feature_auth"
    const val FEATURE_DETAILS = ":feature_details"
    const val FEATURE_SEARCH = ":feature_search"
    const val FEATURE_NAVIGATION = ":feature_navigation"
    const val LIBRARY_BASE = ":library_base"
//    const val LIBRARY_TEST_UTILS = ":library_test_utils"

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
