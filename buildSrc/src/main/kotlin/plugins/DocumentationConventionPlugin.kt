package plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register
import org.jetbrains.dokka.gradle.DokkaPlugin

/**
 * Genesis Protocol - Documentation Convention Plugin
 * Configures Dokka documentation generation for Genesis ecosystem
 */
class DocumentationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // Apply Dokka plugin using modern plugin ID
            pluginManager.apply("org.jetbrains.dokka")

            // Genesis Protocol documentation generation task
            tasks.register("generateGenesisProtocolDocs") {
                group = "genesis"
                description = "Generate complete Genesis Protocol documentation"
                dependsOn("dokkaHtml")

                doLast {
                    project.logger.lifecycle("📚 Genesis Protocol documentation generated successfully!")
                    project.logger.lifecycle("   📄 Location: ${project.layout.buildDirectory.get()}/dokka/html/")
                    project.logger.lifecycle("   🧠 Includes: Genesis, Aura, Kai AI agent documentation")
                    project.logger.lifecycle("   🔧 Includes: DataveinConstructor and OracleDrive APIs")
                }
            }

            // Additional documentation tasks for Genesis ecosystem
            tasks.register("generateFullGenesisEcosystemDocs") {
                group = "genesis"
                description = "Generate comprehensive Genesis ecosystem documentation"
                dependsOn("generateGenesisProtocolDocs")

                doLast {
                    project.logger.lifecycle("🌟 Complete Genesis ecosystem documentation ready!")
                }
            }
        }
    }
}
