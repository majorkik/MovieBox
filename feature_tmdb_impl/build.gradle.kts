plugins {
    id(Plugins.androidLibrary)
    kotlin(Plugins.android)
    id(Plugins.navSafeArgs)
}

android {
    compileSdk = AndroidConfig.compileSdk

    defaultConfig {
        minSdk = AndroidConfig.minSdk
        buildToolsVersion = AndroidConfig.buildTools
    }

    buildFeatures {
        buildConfig = false // Disable generation of BuildConfig files in modules where they are not needed

        aidl = false
        prefab = false
        shaders = false

        compose = false

        dataBinding = false
        viewBinding = false
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

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(project(ModuleDependency.featureTmdbApi))
}
