import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.openapitools.generator.gradle.plugin.OpenApiGeneratorPlugin
import org.openapitools.generator.gradle.plugin.extensions.OpenApiGeneratorGenerateExtension

class OpenApiConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            pluginManager.apply(OpenApiGeneratorPlugin::class.java)

            tasks.register("generateAllApis") {
                group = "genesis"
                description = "Generate all OpenAPI clients for Genesis Protocol"

                dependsOn(
                    "generateAiApi",
                    "generateOracleDriveApi",
                    "generateSandboxApi",
                    "generateSystemApi",
                    "generateCustomizationApi"
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

    private fun Project.configureApiGeneration(apiName: String, specFile: String) {
        tasks.register("generate${apiName.capitalize()}Api", org.openapitools.generator.gradle.plugin.tasks.GenerateTask::class) {
            generatorName.set("kotlin")
            inputSpec.set("$projectDir/api-spec/$specFile.yml")
            outputDir.set("$buildDir/generated/openapi/$apiName")
            packageName.set("dev.aurakai.genesis.api.$apiName")

            configOptions.putAll(mapOf(
                "serializationLibrary" to "kotlinx_serialization",
                "dateLibrary" to "kotlinx-datetime",
                "enumPropertyNaming" to "UPPERCASE"
            ))

            generateModelTests.set(false)
            generateApiTests.set(false)
        }
    }
}
