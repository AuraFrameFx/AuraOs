plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
    // Bleeding edge repositories
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
    maven("https://androidx.dev/storage/compose-compiler/repository/")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(24))
        vendor.set(JvmVendorSpec.ADOPTIUM)
    }
}

kotlin {
    jvmToolchain(24)
}

dependencies {
    // Core Gradle plugins
    implementation("com.android.tools.build:gradle:8.12.0")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.2.0")

    // KSP 2 for Kotlin 2.2.0
    implementation("com.google.devtools.ksp:ksp-gradle-plugin:2.2.0-2.0.2")

    // Hilt DI
    implementation("com.google.dagger:hilt-android-gradle-plugin:2.57")

    // Code Quality
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.23.8")
    implementation("com.diffplug.spotless:spotless-plugin-gradle:7.2.1")

    // Documentation
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:2.0.0")

    // OpenAPI Generator - MISSING BEFORE
    implementation("org.openapi.generator:openapi-generator-gradle-plugin:7.14.0")

    // C++ NDK Native Support - WAS MISSING
    implementation("com.android.tools.build:gradle:8.12.0") // Contains NDK support

    // Serialization
    implementation("org.jetbrains.kotlin:kotlin-serialization:2.2.0")

    // Navigation - WAS MISSING
    implementation("androidx.navigation:navigation-safe-args-gradle-plugin:2.9.3")
}
