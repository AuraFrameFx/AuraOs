plugins {
    // Genesis Protocol Convention Plugins
    id("AndroidLibraryConventionPlugin")
    id("DocumentationConventionPlugin")
    id("ComposeConventionPlugin")

    // Core Kotlin
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
}

android {
    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
        }
    }
}

dependencies {
    // Add module-specific dependencies here
    // Using local JAR files for system interaction
    implementation(files("${project.rootDir}/Libs/api-82.jar"))
    implementation(files("${project.rootDir}/Libs/api-82-sources.jar"))

    // Placeholder for Antaive integration (please specify details if needed)
}
