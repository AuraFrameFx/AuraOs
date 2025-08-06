// Genesis-OS Root Build Configuration with Documentation & Services
plugins {
    alias(libs.plugins.dokka) apply false // Use version from catalog
    // alias(libs.plugins.google.services) apply false // Temporarily disabled
    alias(libs.plugins.spotless) apply false
    // alias(libs.plugins.openapi.generator) apply true // Temporarily disabled
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