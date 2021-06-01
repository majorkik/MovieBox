import com.android.build.gradle.internal.dsl.DefaultConfig

plugins {
    id(Plugins.androidApplication)
    kotlin(Plugins.android)
    id(Plugins.ktlint)
    id(Plugins.navSafeArgs)
    id(Plugins.gms)
    id(Plugins.crashlytics)
}

android {
    compileSdk = AndroidConfig.compileSdk

    defaultConfig {
        applicationId = AndroidConfig.applicationId
        minSdk = AndroidConfig.minSdk
        targetSdk = AndroidConfig.targetSdk
        buildToolsVersion = AndroidConfig.buildTools

        versionCode = AndroidConfig.versionCode
        versionName = AndroidConfig.versionName

        buildConfigFieldFromGradleProperty("keyTmdb")
        buildConfigFieldFromGradleProperty("keyTmdbv4")
        buildConfigFieldFromGradleProperty("youTubeKey")
        buildConfigFieldFromGradleProperty("keyTrakTv")

        buildConfigField(
            "String[]",
            "FEATURE_MODULE_NAMES",
            getDynamicFeatureModuleNames().joinToString(",", prefix = "{", postfix = "}") { "\"$it\""}
        )

        multiDexEnabled = true
    }

    buildTypes {
        getByName(BuildType.RELEASE) {
            isMinifyEnabled = BuildTypeRelease.isMinifyEnabled
            proguardFiles("proguard-android.txt", "proguard-rules.pro")
        }

        getByName(BuildType.DEBUG) {
            isMinifyEnabled = BuildTypeDebug.isMinifyEnabled
        }
    }

    buildFeatures {
        viewBinding = true
    }

    // Each feature module that is included in settings.gradle.kts is added here as dynamic feature
    dynamicFeatures.addAll(ModuleDependency.getDynamicFeatureModules().toMutableSet())

    lintOptions {
        // By default lint does not check test sources, but setting this option means that lint will not even parse them
        isIgnoreTestSources = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    api(project(ModuleDependency.libraryBase))

    implementation(Libs.AndroidX.multidex)
    implementation(Libs.Google.playCore)

    implementation(Libs.Network.OkHttp3.LOGGING_INTERCEPTOR)

    implementation(Libs.Network.retrofitCoroutinesAdapter)
}

tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class.java).configureEach {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

fun com.android.build.api.dsl.BaseFlavor.buildConfigFieldFromGradleProperty(
    gradlePropertyName: String
) {
    val propertyValue =
        com.android.build.gradle.internal.cxx.configure.gradleLocalProperties(rootDir)[gradlePropertyName] as? String
    checkNotNull(propertyValue) { "Gradle property $gradlePropertyName is null" }

    val androidResourceName = "GRADLE_${gradlePropertyName.toSnakeCase()}".toUpperCase()
    buildConfigField("String", androidResourceName, propertyValue)
}

fun getDynamicFeatureModuleNames() = ModuleDependency.getDynamicFeatureModules()
    .map { it.replace(":feature_", "") }
    .toSet()

fun String.toSnakeCase() = this.split(Regex("(?=[A-Z])")).joinToString("_") { it.toLowerCase() }

fun DefaultConfig.buildConfigField(name: String, value: Set<String>) {
    // Generates String that holds Java String Array code
    val strValue = value.joinToString(
        prefix = "{", separator = ",", postfix = "}",
        transform = {
            "\"$it\""
        }
    )
    buildConfigField("String[]", name, strValue)
}
