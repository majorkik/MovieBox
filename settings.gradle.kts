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
                "com.android.dynamic-feature" -> useModule("com.android.tools.build:gradle:3.6.1")
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
