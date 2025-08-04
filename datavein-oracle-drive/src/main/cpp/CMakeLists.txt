cmake_minimum_required(VERSION 3.22.1)

# Set the project name for Oracle Drive ROM Engineering
project("oracle_drive_native")

# Set C++20 standard for modern features
set(CMAKE_CXX_STANDARD 20)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(CMAKE_CXX_EXTENSIONS OFF)

# Enable verbose makefile for debugging
set(CMAKE_VERBOSE_MAKEFILE ON)

# Add compile definitions for ROM engineering
add_definitions(-DENABLE_ROM_ANALYSIS=1)
add_definitions(-DENABLE_NATIVE_LOGGING=1)
add_definitions(-DORACLE_DRIVE_VERSION="2.0.0")

# Include directories
include_directories(
    ${CMAKE_CURRENT_SOURCE_DIR}/include
    ${CMAKE_CURRENT_SOURCE_DIR}/rom_engine
    ${CMAKE_CURRENT_SOURCE_DIR}/compression
    ${CMAKE_CURRENT_SOURCE_DIR}/utils
)

# Find required libraries
find_library(log-lib log)
find_library(android-lib android)
find_library(z-lib z)

# Source files for Oracle Drive ROM Engine
set(ORACLE_DRIVE_SOURCES
    oracle_drive_jni.cpp
    rom_engine/rom_analyzer.cpp
    rom_engine/boot_image_parser.cpp
    rom_engine/partition_manager.cpp
    rom_engine/fastboot_handler.cpp
    compression/compression_engine.cpp
    compression/lz4_handler.cpp
    compression/zstd_handler.cpp
    utils/file_utils.cpp
    utils/crypto_utils.cpp
    utils/logging.cpp
)

# Create the shared library
add_library(
    oracle_drive_native
    SHARED
    ${ORACLE_DRIVE_SOURCES}
)

# Compiler flags for optimization and security
target_compile_options(oracle_drive_native PRIVATE
    -O3
    -ffast-math
    -funroll-loops
    -fvisibility=hidden
    -fstack-protector-strong
    -Wall
    -Wextra
    -Werror
    -Wno-unused-parameter
)

# Link against required libraries
target_link_libraries(
    oracle_drive_native
    ${log-lib}
    ${android-lib}
    ${z-lib}
)

# ARM NEON optimizations for supported architectures
if(ANDROID_ABI STREQUAL "arm64-v8a")
    target_compile_definitions(oracle_drive_native PRIVATE USE_ARM_NEON=1)
    target_compile_options(oracle_drive_native PRIVATE -mfpu=neon)
endif()

if(ANDROID_ABI STREQUAL "armeabi-v7a")
    target_compile_definitions(oracle_drive_native PRIVATE USE_ARM_NEON=1)
    target_compile_options(oracle_drive_native PRIVATE -mfpu=neon-vfpv4)
endif()

# Export symbols for JNI
set_target_properties(oracle_drive_native PROPERTIES
    LINK_FLAGS "-Wl,--export-dynamic"
)
