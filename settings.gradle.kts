pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
    }

    plugins {
        id(GradlePluginId.KTLINT_GRADLE) version GradlePluginVersion.KTLINT_GRADLE
        id(GradlePluginId.GRADLE_VERSION_PLUGIN) version GradlePluginVersion.GRADLE_VERSION_PLUGIN
        id(GradlePluginId.KOTLIN_JVM) version GradlePluginVersion.KOTLIN
        id(GradlePluginId.KOTLIN_ANDROID) version GradlePluginVersion.KOTLIN
        id(GradlePluginId.KOTLIN_ANDROID_EXTENSIONS) version GradlePluginVersion.KOTLIN
        id(GradlePluginId.ANDROID_APPLICATION) version GradlePluginVersion.ANDROID_GRADLE
        id(GradlePluginId.ANDROID_LIBRARY) version GradlePluginVersion.ANDROID_GRADLE
        id(GradlePluginId.ANDROID_DYNAMIC_FEATURE) version GradlePluginVersion.ANDROID_GRADLE
    }

    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                GradlePluginId.ANDROID_APPLICATION,
                GradlePluginId.ANDROID_LIBRARY,
                GradlePluginId.ANDROID_DYNAMIC_FEATURE -> useModule(GradleOldWayPlugins.ANDROID_GRADLE)
            }
        }
    }
}

rootProject.buildFileName = "build.gradle.kts"
include(*ModuleDependency.getAllModules().toTypedArray())
