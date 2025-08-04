package plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register
import org.openapitools.generator.gradle.plugin.OpenApiGeneratorPlugin
import org.openapitools.generator.gradle.plugin.tasks.GenerateTask
import java.util.Locale

class OpenApiConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            pluginManager.apply(OpenApiGeneratorPlugin::class.java)

            tasks.register("generateAllApis") {
                group = "genesis"
                description = "Generate all OpenAPI clients for Genesis Protocol"

                dependsOn(
                    "generateAiApiClient",
                    "generateOracleDriveApiClient",
                    "generateSandboxApiClient",
                    "generateSystemApiClient",
                    "generateCustomizationApiClient"
                )
            }

            // Configure individual API generators
            configureApiGeneration("ai", "ai-api")
            configureApiGeneration("oracle-drive", "oracle-drive-api")
            configureApiGeneration("sandbox", "sandbox-api")
            configureApiGeneration("system", "system-api")
            configureApiGeneration("customization", "customization-api")
        }
    }

    private fun Project.configureApiGeneration(apiName: String, specFileName: String) {
        tasks.register<GenerateTask>("generate${apiName.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }}ApiClient") {
            group = "openapi"
            description = "Generate $apiName API client"

            generatorName.set("kotlin")
            inputSpec.set("${rootProject.projectDir}/api-spec/${specFileName}.yml")
            outputDir.set("${layout.buildDirectory.get()}/generated/openapi/${apiName}")
            packageName.set("${project.group}.${project.name}.api.${apiName}")

            configOptions.set(mapOf(
                "useCoroutines" to "true",
                "serializationLibrary" to "kotlinx_serialization",
                "enumPropertyNaming" to "UPPERCASE",
                "parcelizeModels" to "true"
            ))

            generateModelTests.set(false)
            generateApiTests.set(false)
        }
    }
}
