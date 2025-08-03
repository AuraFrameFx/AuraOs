import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.dokka.gradle.DokkaPlugin

class DocumentationConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            pluginManager.apply(DokkaPlugin::class.java)

            tasks.register("generateGenesisProtocolDocs") {
                group = "genesis"
                description = "Generate complete Genesis Protocol documentation"
                dependsOn("dokkaHtml")
            }
        }
    }
}
