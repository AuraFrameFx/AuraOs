import org.openapitools.generator.gradle.plugin.tasks.GenerateTask

// This script assumes the OpenAPI Generator plugin is applied in the consuming build.gradle.kts
// Example: plugins { id("org.openapi.generator") version "7.1.0" }
// Then: apply(from = rootProject.file("openapi-generator.gradle.kts"))

// Register OpenAPI codegen tasks for each API spec
val openapiSpecs = listOf(
    Triple("ai", "ai-api.yml", "dev.aurakai.auraframefx.api.ai"),
    Triple("customization", "customization-api.yml", "dev.aurakai.auraframefx.api.customization"),
    Triple("genesis", "genesis-api.yml", "dev.aurakai.auraframefx.api.genesis"),
    Triple("oracleDrive", "oracle-drive-api.yml", "dev.aurakai.auraframefx.api.oracledrive"),
    Triple("sandbox", "sandbox-api.yml", "dev.aurakai.auraframefx.api.sandbox"),
    Triple("system", "system-api.yml", "dev.aurakai.auraframefx.api.system")
)

openapiSpecs.forEach { (name, spec, pkg) ->
    tasks.register("generate${name.replaceFirstChar { it.uppercase() }}ApiClient", GenerateTask::class) {
        generatorName.set("kotlin")
        library.set("jvm-retrofit2")
        inputSpec.set("${rootDir}/api-spec/$spec")
        outputDir.set(layout.buildDirectory.dir("generated/openapi/$name").get().asFile.absolutePath)
        packageName.set(pkg)
        configFile.set("${rootDir}/openapi-generator-config.json")
    }
}

// Modern Kotlin DSL for source sets (not deprecated)
plugins.withId("com.android.application") {
    extensions.configure<com.android.build.gradle.internal.dsl.BaseAppModuleExtension> {
        sourceSets["main"].java.srcDir("${buildDir}/generated/openapi/ai/src/main/kotlin")
        sourceSets["main"].java.srcDir("${buildDir}/generated/openapi/customization/src/main/kotlin")
        sourceSets["main"].java.srcDir("${buildDir}/generated/openapi/genesis/src/main/kotlin")
        sourceSets["main"].java.srcDir("${buildDir}/generated/openapi/oracleDrive/src/main/kotlin")
        sourceSets["main"].java.srcDir("${buildDir}/generated/openapi/sandbox/src/main/kotlin")
        sourceSets["main"].java.srcDir("${buildDir}/generated/openapi/system/src/main/kotlin")
    }
}

// Make codegen tasks run before build
afterEvaluate {
    tasks.findByName("preBuild")?.dependsOn(
        "generateAiApiClient",
        "generateCustomizationApiClient",
        "generateGenesisApiClient",
        "generateOracleDriveApiClient",
        "generateSandboxApiClient",
        "generateSystemApiClient"
    )
}
