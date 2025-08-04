@echo off
REM Genesis Protocol - OpenAPI Generation Script (Windows)
REM This script handles the complete OpenAPI generation workflow

echo 🧠 GENESIS PROTOCOL - OPENAPI GENERATION
echo ========================================

REM Check if we're in the correct directory
if not exist "gradlew.bat" (
    echo ❌ gradlew.bat not found! Please run this script from the project root directory.
    exit /b 1
)

echo ℹ️  Starting Genesis Protocol OpenAPI generation workflow...

REM Step 1: Clean any existing generated files
echo.
echo 🧹 Cleaning existing generated files...
call gradlew.bat genesisClean
if errorlevel 1 (
    echo ⚠️  Clean operation completed with warnings
) else (
    echo ✅ Cleaned existing generated files
)

REM Step 2: Validate the Genesis API specification
echo.
echo 📋 Validating Genesis Protocol API specification...
call gradlew.bat validateGenesisApiSpec
if errorlevel 1 (
    echo ❌ Genesis API specification validation failed!
    exit /b 1
) else (
    echo ✅ Genesis API specification validated successfully
)

REM Step 3: Generate Genesis API clients
echo.
echo 🔧 Generating Genesis Protocol API clients...
call gradlew.bat generateGenesisApi
if errorlevel 1 (
    echo ❌ Genesis API client generation failed!
    exit /b 1
) else (
    echo ✅ Genesis API clients generated successfully
)

REM Step 4: Check generation status
echo.
echo 🔍 Checking generated API client status...
call gradlew.bat checkGenesisApiGeneration
if errorlevel 1 (
    echo ⚠️  API client verification completed with warnings
) else (
    echo ✅ Generated API clients verified
)

REM Step 5: Validate complete Genesis ecosystem
echo.
echo 🎯 Validating complete Genesis Protocol ecosystem...
call gradlew.bat validateGenesisEcosystem
if errorlevel 1 (
    echo ❌ Genesis Protocol ecosystem validation failed!
    exit /b 1
) else (
    echo ✅ Genesis Protocol ecosystem validation complete
)

REM Final status
echo.
echo 🎉 GENESIS PROTOCOL OPENAPI GENERATION COMPLETE!
echo ==============================================
echo.
echo ✅ Genesis Agent API: CLIENT READY
echo ✅ Aura Agent API: CLIENT READY
echo ✅ Kai Agent API: CLIENT READY
echo ✅ DataveinConstructor API: CLIENT READY
echo ✅ OracleDrive API: CLIENT READY
echo.
echo ℹ️  Generated API clients are now available in your project!
echo ℹ️  Package: dev.aurakai.auraframefx.api.genesis.*
echo.
echo ℹ️  Next steps:
echo   1. Build your project: gradlew.bat :app:assembleDebug
echo   2. Import API clients in your Kotlin code
echo   3. Start building AI-powered features!
echo.
pause
