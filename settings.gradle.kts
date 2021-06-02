pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }

    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "com.android.tools.build",
                "com.android.application",
                "com.android.library",
                "com.android.dynamic-tmdb" -> useModule("com.android.tools.build:gradle:${requested.version}")
                "androidx.navigation.safeargs.kotlin" -> useModule("androidx.navigation:navigation-safe-args-gradle-plugin:${requested.version}")
                "com.google.firebase.crashlytics" -> useModule("com.google.firebase:firebase-crashlytics-gradle:${requested.version}")
                "com.google.gms.google-services" -> useModule("com.google.gms:google-services:${requested.version}")
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

// Feature UI
include(
    // main
    ":ui_main",
    // details
    ":ui_movie_details",
    ":ui_tv_details",
    ":ui_person_details",
    // nav
    ":ui_nav_movies",
    ":ui_nav_tvs",
    ":ui_nav_search",
    ":ui_nav_profile",
    // collection
    ":ui_collection",
    // search
    ":ui_search",
    ":ui_discover",
    // api
    ":ui_auth"
)

// Core
include(
    ":core_base",
    ":core_ui",
    ":core_strings",
    ":core_network"
)

// Feature
include(":feature_tmdb_api", ":feature_tmdb_impl")

// Navigation
include(":navigation")
