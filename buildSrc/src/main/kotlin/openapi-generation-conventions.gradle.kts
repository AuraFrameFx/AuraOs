import org.openapitools.generator.gradle.plugin.OpenApiGeneratorPlugin
import org.openapitools.generator.gradle.plugin.tasks.GenerateTask
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.AppExtension

plugins {
    id("org.openapi.generator")
}

// Apply the OpenAPI generator plugin
apply<OpenApiGeneratorPlugin>()

// Define the API specifications available
val apiSpecs = mapOf(
    "ai" to "ai-api.yml",
    "oracle-drive" to "oracle-drive-api.yml",
    "customization" to "customization-api.yml",
    "sandbox" to "sandbox-api.yml",
    "system" to "system-api.yml"
)

// Configure OpenAPI generation tasks for each API specification
apiSpecs.forEach { (apiName, specFile) ->
    tasks.register<GenerateTask>("generate${apiName.capitalize()}ApiClient") {
        group = "openapi"
        description = "Generate $apiName API client from OpenAPI specification"

        // Look for API spec in multiple locations
        val projectSpecFile = file("src/main/openapi/${specFile}")
        val rootSpecFile = rootProject.file("api-spec/${specFile}")
        val rootOpenApiFile = rootProject.file("openapi.yml")

        // Use project-specific spec if it exists, otherwise use root api-spec, fallback to root openapi.yml
        inputSpec.set(
            when {
                projectSpecFile.exists() -> projectSpecFile.absolutePath
                rootSpecFile.exists() -> rootSpecFile.absolutePath
                rootOpenApiFile.exists() -> rootOpenApiFile.absolutePath
                else -> throw GradleException("No OpenAPI specification found for $apiName")
            }
        )

        outputDir.set("${project.layout.buildDirectory.get()}/generated/openapi/${apiName}")

        // Configuration from openapi-generator-config.json
        generatorName.set("kotlin")
        library.set("jvm-retrofit2")

        configOptions.putAll(mapOf(
            "useCoroutines" to "true",
            "serializationLibrary" to "kotlinx_serialization",
            "enumPropertyNaming" to "UPPERCASE",
            "parcelizeModels" to "true",
            "dateLibrary" to "java8",
            "collectionType" to "list",
            "packageName" to "${project.group}.${project.name}.api.${apiName}",
            "apiPackage" to "${project.group}.${project.name}.api.${apiName}.client",
            "modelPackage" to "${project.group}.${project.name}.api.${apiName}.model"
        ))

        // Ignore files we don't need
        ignoreFileOverride.set("${rootProject.projectDir}/.openapi-generator-ignore")
    }
}

// Create a combined task to generate all API clients
tasks.register("generateAllApiClients") {
    group = "openapi"
    description = "Generate all API clients from OpenAPI specifications"
    dependsOn(apiSpecs.keys.map { "generate${it.capitalize()}ApiClient" })
}

// Configure Android source sets to include generated code
afterEvaluate {
    val androidExtension = extensions.findByType<LibraryExtension>()
        ?: extensions.findByType<AppExtension>()

    androidExtension?.let { android ->
        android.sourceSets.getByName("main") {
            java.srcDirs("${project.layout.buildDirectory.get()}/generated/openapi")
        }
    }
}
