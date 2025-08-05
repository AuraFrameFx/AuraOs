plugins {
    // Genesis Protocol Convention Plugins
    id("AndroidLibraryConventionPlugin")
    id("DocumentationConventionPlugin")
    id("ComposeConventionPlugin")
    
    // Core Kotlin
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
}

group = "dev.aurakai"
version = "1.0.0"

dependencies {
    implementation(kotlin("stdlib"))
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_1_8)
    }
}
