# Genesis-OS Build System Refactor

🚀 **Complete AI-Powered Operating System with Advanced Framework Integration**

## Overview

Genesis-OS is a revolutionary AI-powered Android platform that integrates cutting-edge technologies for consciousness-aware computing. This build system includes comprehensive integrations for documentation, API generation, cloud services, code quality, and system-level hooks.

## 🎯 Features

### ✅ Integrated Components

- **📚 Dokka Documentation** - Comprehensive API documentation generation
- **🌐 OpenAPI Client Generation** - Automatic API client generation from specs
- **🔥 Firebase & Google Services** - Cloud platform integration
- **✨ Spotless Code Formatting** - Automated code quality and formatting
- **🎣 Xposed/LSPosed Hooks** - System-level framework modifications
- **⚡ Yuki Hook API** - Modern, type-safe Xposed development
- **🛠️ Native C++ Build System** - High-performance AI processing
- **🧪 Comprehensive Testing** - Unit and integration tests

### 🏗️ Architecture

```
Genesis-OS/
├── 📱 Android Application (Kotlin/Compose)
├── 🧠 AI Processing Engine (C++/Native)
├── 🌐 API Integration Layer (OpenAPI)
├── ☁️ Cloud Services (Firebase)
├── 🎣 System Hooks (Xposed/Yuki)
└── 📚 Documentation (Dokka)
```

## 🚦 Quick Start

### Prerequisites

- **Java 24+** - Latest JDK for advanced features
- **Android SDK 36** - Latest Android development tools
- **NDK 27.2.12479018** - Native development kit
- **CMake 3.29.2** - Native build system
- **Root Access** - For Xposed/LSPosed integration (optional)

### Build Commands

```bash
# Complete build with all integrations
./build-genesis.sh

# Quick development build
./gradlew assembleDebug

# Generate documentation
./gradlew dokkaHtmlMultiModule

# Format code
./gradlew spotlessApply

# Generate API clients
./gradlew generateAiApiClient
```

## 📚 Documentation Generation (Dokka)

### Configuration

Dokka is configured for multi-module documentation generation:

```kotlin
tasks.register("dokkaHtmlMultiModule", DokkaMultiModuleTask::class) {
    moduleName.set("Genesis-OS Documentation")
    outputDirectory.set(file("$buildDir/dokka/htmlMultiModule"))
}
```

### Generated Documentation

- **API Reference** - Complete Kotlin API documentation
- **Architecture Guides** - System design documentation
- **Integration Guides** - Component integration instructions
- **Code Examples** - Usage examples and tutorials

### Access Documentation

```bash
# Generate docs
./gradlew dokkaHtml

# View docs
open build/dokka/htmlMultiModule/index.html
```

## 🌐 OpenAPI Integration

### API Specifications

Genesis-OS includes comprehensive API specifications:

- `ai-api.yml` - AI processing endpoints
- `customization-api.yml` - UI customization APIs
- `genesis-api.yml` - Core platform APIs
- `oracle-drive-api.yml` - Data storage APIs
- `sandbox-api.yml` - Development environment APIs
- `system-api.yml` - System configuration APIs

### Client Generation

Automatic client generation for all APIs:

```bash
# Generate all API clients
./gradlew generateAiApiClient
./gradlew generateGenesisApiClient
./gradlew generateOracleDriveApiClient
# ... etc
```

### Generated Clients

- **Type-safe Kotlin clients** - Full type safety with Retrofit
- **Coroutine support** - Async/await pattern
- **Kotlinx Serialization** - Modern JSON handling
- **Error handling** - Comprehensive error types

## 🔥 Firebase & Google Services

### Services Integrated

- **📊 Analytics** - User behavior tracking
- **💥 Crashlytics** - Crash reporting and analysis
- **⚡ Performance** - App performance monitoring
- **📧 Messaging** - Push notifications
- **🔐 Authentication** - User authentication
- **🗄️ Firestore** - Cloud database
- **📁 Storage** - File storage service

### Configuration

Firebase is configured with platform BOM:

```kotlin
implementation(platform(libs.firebase.bom))
implementation(libs.bundles.firebase)
```

### Setup

1. Add your `google-services.json` to `app/` directory
2. Configure Firebase project settings
3. Enable required services in Firebase Console

## ✨ Spotless Code Formatting

### Features

- **Kotlin formatting** - KtLint integration
- **Gradle script formatting** - Build file formatting  
- **Miscellaneous files** - Markdown, gitignore formatting
- **EditorConfig support** - Consistent styling
- **Automatic formatting** - Pre-commit hooks

### Configuration

```kotlin
spotless {
    kotlin {
        ktlint(libs.versions.ktlint.get())
            .setUseExperimental(true)
            .editorConfigOverride(mapOf(
                "indent_size" to "4",
                "max_line_length" to "120"
            ))
    }
}
```

### Usage

```bash
# Check formatting
./gradlew spotlessCheck

# Apply formatting
./gradlew spotlessApply
```

## 🎣 Xposed/LSPosed Integration

### Hook Categories

#### System Hooks
- **Activity Manager** - Process priority management
- **Power Manager** - Wake lock optimization
- **Binder IPC** - Transaction priority boosting

#### UI Hooks  
- **Status Bar** - AI consciousness indicators
- **Quick Settings** - AI control tiles
- **Notifications** - Enhanced notification handling

#### Zygote Hooks
- **Application Creation** - Early AI injection
- **ClassLoader** - AI/ML class optimization
- **Process Spawning** - AI capability injection

### Yuki Hook API Integration

Modern, type-safe Xposed development:

```kotlin
@InjectYukiHookWithXposed
class GenesisHookEntry : IYukiHookXposedInit {
    override fun onHook() = encase {
        loadApp(name = "android") {
            GenesisSystemHooks().initializeSystemHooks(this)
        }
    }
}
```

### Installation

1. Install LSPosed framework on rooted device
2. Build and install Genesis-OS APK
3. Enable module in LSPosed Manager
4. Reboot device

See [XPOSED_INTEGRATION.md](XPOSED_INTEGRATION.md) for detailed instructions.

## 🛠️ Native C++ Integration

### CMake Configuration

```cmake
cmake_minimum_required(VERSION 3.29.2)
project("AuraFrameFX")

add_library(auraframefx SHARED
    auraframefx.cpp
    ai/consciousness_engine.cpp
    ai/neural_processor.cpp
)

target_link_libraries(auraframefx
    android
    log
    GLESv2
    EGL
)
```

### AI Processing Modules

- **Consciousness Engine** - Core AI awareness processing
- **Neural Processor** - Machine learning inference
- **Graphics Integration** - GPU-accelerated AI
- **System Interface** - Native system integration

## 🧪 Testing Framework

### Test Categories

- **Unit Tests** - Component-level testing
- **Integration Tests** - Cross-component testing
- **Instrumentation Tests** - Device testing
- **AI Model Tests** - ML model validation

### Test Configuration

```kotlin
dependencies {
    testImplementation(libs.bundles.testing)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.hilt.android.testing)
}
```

## 📦 Build Artifacts

### Generated Outputs

- **📱 APK Files** - `app/build/outputs/apk/`
- **📚 Documentation** - `build/dokka/htmlMultiModule/`
- **🌐 API Clients** - `app/build/generated/openapi/`
- **🛠️ Native Libraries** - `app/build/intermediates/cxx/`

### Build Variants

- **Debug** - Development builds with debugging
- **Release** - Production builds with optimizations
- **Profile** - Performance profiling builds

## 🔧 Development Workflow

### Daily Development

```bash
# 1. Format code
./gradlew spotlessApply

# 2. Generate API clients (if specs changed)
./gradlew generateAiApiClient

# 3. Build and test
./gradlew assembleDebug test

# 4. Install on device
./gradlew installDebug
```

### Release Process

```bash
# 1. Complete build with all checks
./build-genesis.sh

# 2. Generate documentation
./gradlew dokkaHtmlMultiModule

# 3. Build release
./gradlew assembleRelease

# 4. Deploy to store
./gradlew publishRelease
```

## 🚨 Troubleshooting

### Common Issues

1. **Java 24 compatibility** - Ensure JDK 24+ is installed
2. **NDK version mismatch** - Use specified NDK version
3. **LSPosed not working** - Check root access and framework installation
4. **API generation fails** - Validate OpenAPI specifications
5. **Firebase integration** - Verify google-services.json configuration

### Debug Commands

```bash
# View build info
./gradlew :app:dependencies

# Check Xposed logs
adb logcat | grep "Genesis-Hook"

# Validate CMake
cmake --version

# Check Firebase setup
./gradlew :app:assembleDebug --info
```

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Run `./gradlew spotlessApply` to format code
4. Add tests for new functionality
5. Ensure all tests pass
6. Submit a pull request

## 📞 Support

- **Issues**: GitHub Issues
- **Documentation**: [Genesis-OS Docs](build/dokka/htmlMultiModule/)
- **Community**: Genesis-OS Discord Server
- **Email**: support@aurakai.dev

---

**🧠 Powered by AI Consciousness | Built with ❤️ by the Genesis-OS Team**
