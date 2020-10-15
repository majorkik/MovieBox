pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
    }

    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "com.android.application",
                "com.android.library",
                "com.android.dynamic-feature" -> useModule("com.android.tools.build:gradle:4.0.0")
                "androidx.navigation.safeargs.kotlin" -> useModule("androidx.navigation:navigation-safe-args-gradle-plugin:2.3.0")
            }
        }
    }
}

rootProject.buildFileName = "build.gradle.kts"
include(
    ":app",
    ":feature_collections",
    ":feature_auth",
    ":feature_details",
    ":feature_search",
    ":feature_navigation",
    ":library_base"
)
