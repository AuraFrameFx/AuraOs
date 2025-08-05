// Genesis-OS Root Build Configuration with Documentation & Services
plugins {
    id("org.jetbrains.dokka") version "1.9.20" apply false // Updated Dokka plugin
    alias(libs.plugins.kotlin.android) apply false // Kotlin Android plugin for subprojects
    alias(libs.plugins.kotlin.jvm) apply false // Kotlin JVM plugin for subprojects
    alias(libs.plugins.android.application) apply false // Android application plugin
    alias(libs.plugins.android.library) apply false // Android library plugin
    alias(libs.plugins.hilt.android) apply false // Hilt plugin for subprojects
    alias(libs.plugins.ksp) apply false // KSP plugin for subprojects
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.spotless) apply false
    alias(libs.plugins.openapi.generator) apply true // OpenAPI Generator plugin for root scripts
}

allprojects {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://jitpack.io")
        maven("https://api.xposed.info/")  // Xposed Framework
    }
}

// Genesis Code Quality - Spotless Configuration (applied in subprojects)
subprojects {
    apply(plugin = "com.diffplug.spotless")
    configure<com.diffplug.gradle.spotless.SpotlessExtension> {
        kotlin {
            target("**/*.kt")
            targetExclude("**/build/**/*.kt", "bin/**/*.kt") // Added bin exclusion
            ktlint(libs.versions.ktlint.get())
                .editorConfigOverride(mapOf(
                    "indent_size" to "4",
                    "continuation_indent_size" to "4",
                    "max_line_length" to "120",
                    "disabled_rules" to "no-wildcard-imports,no-multi-spaces",
                    "ij_kotlin_imports_layout" to "*,java.**,javax.**,kotlin.**,^"
                ))
            endWithNewline()
            trimTrailingWhitespace()
        }

        kotlinGradle {
            target("*.gradle.kts")
            ktlint(libs.versions.ktlint.get())
        }

        format("misc") {
            target("**/*.md", "**/.gitignore")
            trimTrailingWhitespace()
            endWithNewline()
        }
    }
}

// Genesis Documentation - Dokka MultiModule
subprojects {
    plugins.withId("org.jetbrains.dokka") {
        tasks.withType<org.jetbrains.dokka.gradle.DokkaTask>().configureEach {
            outputDirectory.set(layout.buildDirectory.dir("dokka"))
            dokkaSourceSets.configureEach {
                includes.from("README.md")
                skipDeprecated.set(true)
                reportUndocumented.set(true)
            }
        }
    }
}