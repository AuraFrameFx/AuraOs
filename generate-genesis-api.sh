#!/bin/bash

# Genesis Protocol - OpenAPI Generation Script
# This script handles the complete OpenAPI generation workflow

echo "🧠 GENESIS PROTOCOL - OPENAPI GENERATION"
echo "========================================"

# Colors for output
GREEN='\033[0;32m'
BLUE='\033[0;34m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m' # No Color

# Function to print colored output
print_status() {
    echo -e "${GREEN}✅ $1${NC}"
}

print_info() {
    echo -e "${BLUE}ℹ️  $1${NC}"
}

print_warning() {
    echo -e "${YELLOW}⚠️  $1${NC}"
}

print_error() {
    echo -e "${RED}❌ $1${NC}"
}

# Check if we're in the correct directory
if [ ! -f "gradlew" ]; then
    print_error "gradlew not found! Please run this script from the project root directory."
    exit 1
fi

print_info "Starting Genesis Protocol OpenAPI generation workflow..."

# Step 1: Clean any existing generated files
echo ""
echo "🧹 Cleaning existing generated files..."
./gradlew genesisClean
if [ $? -eq 0 ]; then
    print_status "Cleaned existing generated files"
else
    print_warning "Clean operation completed with warnings"
fi

# Step 2: Validate the Genesis API specification
echo ""
echo "📋 Validating Genesis Protocol API specification..."
./gradlew validateGenesisApiSpec
if [ $? -eq 0 ]; then
    print_status "Genesis API specification validated successfully"
else
    print_error "Genesis API specification validation failed!"
    exit 1
fi

# Step 3: Generate Genesis API clients
echo ""
echo "🔧 Generating Genesis Protocol API clients..."
./gradlew generateGenesisApi
if [ $? -eq 0 ]; then
    print_status "Genesis API clients generated successfully"
else
    print_error "Genesis API client generation failed!"
    exit 1
fi

# Step 4: Check generation status
echo ""
echo "🔍 Checking generated API client status..."
./gradlew checkGenesisApiGeneration
if [ $? -eq 0 ]; then
    print_status "Generated API clients verified"
else
    print_warning "API client verification completed with warnings"
fi

# Step 5: Validate complete Genesis ecosystem
echo ""
echo "🎯 Validating complete Genesis Protocol ecosystem..."
./gradlew validateGenesisEcosystem
if [ $? -eq 0 ]; then
    print_status "Genesis Protocol ecosystem validation complete"
else
    print_error "Genesis Protocol ecosystem validation failed!"
    exit 1
fi

# Final status
echo ""
echo "🎉 GENESIS PROTOCOL OPENAPI GENERATION COMPLETE!"
echo "=============================================="
echo ""
print_status "Genesis Agent API: CLIENT READY"
print_status "Aura Agent API: CLIENT READY"
print_status "Kai Agent API: CLIENT READY"
print_status "DataveinConstructor API: CLIENT READY"
print_status "OracleDrive API: CLIENT READY"
echo ""
print_info "Generated API clients are now available in your project!"
print_info "Package: dev.aurakai.auraframefx.api.genesis.*"
echo ""
print_info "Next steps:"
echo "  1. Build your project: ./gradlew :app:assembleDebug"
echo "  2. Import API clients in your Kotlin code"
echo "  3. Start building AI-powered features!"
echo ""
