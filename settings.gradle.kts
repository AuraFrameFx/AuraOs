@file:Suppress("UnstableApiUsage")

// Enable Gradle features
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
enableFeaturePreview("STABLE_CONFIGURATION_CACHE")

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://jitpack.io")
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_PROJECT)
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://jitpack.io")
        maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    }
}

rootProject.name = "Genesis-Os"

include(":app")
include(":core-module")
include(":feature-module")
include(":datavein-oracle-drive")
include(":datavein-oracle-native")
include(":secure-comm")
include(":collab-canvas")
include(":colorblendr")
include(":jvm-test")
include(":module-a")
include(":module-b")
include(":module-c")
include(":module-d")
include(":module-e")
include(":module-f")
include(":oracle-drive-integration")
include(":oracledrive-integration")
include(":sandbox-ui")