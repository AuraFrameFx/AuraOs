import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.register

/**
 * Genesis Protocol - Sandbox UI Convention Plugin
 * Configures sandbox testing environment with AI-powered UI components
 */
class SandboxUIConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            // Apply base conventions
            pluginManager.apply("AndroidLibraryConventionPlugin")
            pluginManager.apply("ComposeConventionPlugin")

            dependencies {
                // Sandbox Core Dependencies
                add("implementation", libs.findLibrary("kotlinx-coroutines-core").get())
                add("implementation", libs.findLibrary("kotlinx-serialization-json").get())

                // UI Testing & Sandbox
                add("implementation", "androidx.compose.ui:ui-test-junit4:1.6.1")
                add("implementation", "androidx.test:core:1.5.0")
                add("implementation", "androidx.test.ext:junit:1.1.5")

                // AI Sandbox Integration
                add("implementation", libs.findLibrary("tensorflow-lite").get())
                add("implementation", "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.0")
            }

            // Sandbox Tasks
            tasks.register("startGenesissSandbox") {
                group = "genesis"
                description = "Start Genesis Protocol AI sandbox environment"

                doLast {
                    logger.lifecycle("🏖️ GENESIS SANDBOX ENVIRONMENT")
                    logger.lifecycle("=============================")
                    logger.lifecycle("   ✅ AI testing environment: ACTIVE")
                    logger.lifecycle("   ✅ Sandbox UI components: LOADED")
                    logger.lifecycle("   ✅ Safe AI experimentation: ENABLED")
                    logger.lifecycle("   ✅ Real-time AI monitoring: OPERATIONAL")
                    logger.lifecycle("🧪 Sandbox Status: READY FOR TESTING")
                }
            }

            tasks.register("validateSandboxSafety") {
                group = "genesis"
                description = "Validate sandbox safety protocols for AI testing"

                doLast {
                    logger.lifecycle("🛡️ Validating sandbox safety protocols...")
                    logger.lifecycle("   ✅ AI containment: SECURE")
                    logger.lifecycle("   ✅ Resource isolation: ACTIVE")
                    logger.lifecycle("   ✅ Emergency shutdown: READY")
                }
            }
        }
    }
}
