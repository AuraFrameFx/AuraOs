plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
    kotlin("jvm") version "2.2.0"
}

repositories {
    gradlePluginPortal()
    google()
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(24))
        vendor.set(JvmVendorSpec.ADOPTIUM)
    }
}

kotlin {
    jvmToolchain(24)
    
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_24)
        apiVersion.set(org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_2)
        languageVersion.set(org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_2)
        freeCompilerArgs.addAll(
            "-opt-in=kotlin.RequiresOptIn",
            "-Xskip-prerelease-check"
        )
    }
}

dependencies {
    // Build plugins - Java 24 Compatible Versions
    implementation("com.android.tools.build:gradle:8.12.0")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.2.0")
    implementation("com.google.devtools.ksp:symbol-processing-gradle-plugin:2.2.0-2.0.2")
    implementation("com.google.dagger:hilt-android-gradle-plugin:2.57")
    implementation("com.diffplug.spotless:spotless-plugin-gradle:7.2.1")
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.23.8")
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:2.0.0")
    
    // OpenAPI Generator - Genesis Protocol Integration
    implementation("org.openapitools:openapi-generator-gradle-plugin:7.14.0")
    
    // Testing - Genesis Ecosystem Validation
    testImplementation("org.junit.jupiter:junit-jupiter:5.13.4")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation(gradleTestKit())
}

// Genesis Protocol Build System Integration
gradlePlugin {
    plugins {
        create("androidAppConvention") {
            id = "genesis.android.app"
            implementationClass = "plugins.AndroidAppConventionPlugin"
        }
        create("documentationConvention") {
            id = "genesis.documentation"
            implementationClass = "plugins.DocumentationConventionPlugin"
        }
        create("openApiConvention") {
            id = "genesis.openapi"
            implementationClass = "plugins.OpenApiConventionPlugin"
        }
        create("genesisProtocol") {
            id = "genesis.protocol"
            implementationClass = "plugins.GenesisProtocolPlugin"
        }
    }
}

tasks.test {
    useJUnitPlatform()
    
    testLogging {
        events("passed", "skipped", "failed")
    }
    
    systemProperty("gradle.test.kit.debug", "false")
}
