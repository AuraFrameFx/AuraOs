plugins {
    // Core Gradle plugins only - All other plugins applied through buildSrc conventions
    // Removed ALL conflicting plugins that are used in buildSrc:
    // - android.application/library (used in AndroidAppConventionPlugin)
    // - kotlin plugins (used in convention plugins)
    // - spotless (used in spotless-conventions.gradle.kts)
    // - detekt (used in detekt-conventions.gradle.kts)
    // - dokka (used in DocumentationConventionPlugin)
}

// NO plugin declarations here - they're all applied through buildSrc conventions
// This prevents "plugin already on classpath" conflicts

// Configure subprojects with modern approach - Only apply to valid Android modules
subprojects {
    // Only apply Genesis Protocol plugin to modules that need it
    if (name in listOf("app", "collab-canvas", "datavein-oracle-drive", "sandbox-ui", "secure-comm")) {
        apply<plugins.GenesisProtocolPlugin>()
    }

    // Apply formatting conventions only to modules with Kotlin code
    if (file("src").exists()) {
        apply(from = "${rootProject.projectDir}/buildSrc/src/main/kotlin/spotless-conventions.gradle.kts")
    }
}

// Clean task
tasks.register<Delete>("clean") {
    delete(rootProject.layout.buildDirectory)
}

// Root project repositories - available to all subprojects
allprojects {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}