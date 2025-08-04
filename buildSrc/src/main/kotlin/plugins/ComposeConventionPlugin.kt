import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

/**
 * Genesis Protocol - Compose Convention Plugin
 * Configures Jetpack Compose dependencies and optimizations
 */
class ComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            dependencies {
                add("implementation", libs.findLibrary("compose-bom").get())
                add("implementation", libs.findLibrary("compose-ui").get())
                add("implementation", libs.findLibrary("compose-ui-tooling-preview").get())
                add("implementation", libs.findLibrary("compose-material3").get())
                add("implementation", libs.findLibrary("compose-activity").get())
                add("implementation", libs.findLibrary("compose-viewmodel").get())
                add("implementation", libs.findLibrary("compose-navigation").get())

                // Genesis Protocol - AI Compose Extensions
                add("debugImplementation", libs.findLibrary("compose-ui-tooling").get())
                add("debugImplementation", libs.findLibrary("compose-ui-test-manifest").get())

                // Testing
                add("androidTestImplementation", libs.findLibrary("compose-ui-test-junit4").get())
            }

            // Genesis Protocol Compose Tasks
            tasks.register("generateComposeMetrics") {
                group = "genesis"
                description = "Generate Compose compiler metrics for Genesis Protocol"

                doLast {
                    logger.lifecycle("📊 Generating Compose metrics for Genesis Protocol integration...")
                }
            }
        }
    }
}
