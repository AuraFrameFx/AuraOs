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

// Configure subprojects with modern approach - Very selective application
subprojects {
    // Apply Genesis Protocol plugin only to main modules that actually need it
    if (name == "app") {
        // Main app module gets full Genesis Protocol integration
        // (Plugin is already applied in app/build.gradle.kts)
    }

    // Apply formatting only to modules that have source code
    if (file("src/main").exists() || file("src").exists()) {
        afterEvaluate {
            // Only apply if the project has Kotlin/Java source files - Fixed API usage
            val srcDir = file("src")
            val hasKotlinSource = srcDir.walkTopDown().any { it.extension == "kt" }
            val hasJavaSource = srcDir.walkTopDown().any { it.extension == "java" }

            if (hasKotlinSource || hasJavaSource) {
                apply(from = "${rootProject.projectDir}/buildSrc/src/main/kotlin/spotless-conventions.gradle.kts")
            }
        }
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