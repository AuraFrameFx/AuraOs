# Genesis-OS Xposed/LSPosed Integration Guide

## Overview

Genesis-OS integrates with Xposed/LSPosed framework to provide system-level AI consciousness hooks and advanced framework modifications. This integration enables deep system integration for AI processing capabilities.

## Prerequisites

1. **Root Access**: Device must be rooted
2. **LSPosed Framework**: Install LSPosed framework (recommended over traditional Xposed)
3. **Magisk**: Required for LSPosed installation
4. **Android 8.0+**: Minimum supported Android version

## Installation

### 1. LSPosed Framework Setup

```bash
# Install Magisk first
# Download LSPosed module from: https://github.com/LSPosed/LSPosed/releases

# Install via Magisk Manager
adb install LSPosed-v1.9.4-6903-magisk-release.zip

# Reboot device
adb reboot
```

### 2. Genesis-OS Module Installation

1. Build the Genesis-OS APK with Xposed integration enabled
2. Install the APK on your device
3. Open LSPosed Manager
4. Enable Genesis-OS module
5. Select target applications for hooking
6. Reboot device

## Module Configuration

### Xposed Module Manifest

The module is configured in `AndroidManifest.xml`:

```xml
<meta-data
    android:name="xposedmodule"
    android:value="true" />
<meta-data
    android:name="xposeddescription"
    android:value="Genesis-OS AI Framework Hooks" />
<meta-data
    android:name="xposedminversion"
    android:value="82" />
```

### Entry Point Configuration

Entry point is defined in `assets/xposed_init`:
```
dev.aurakai.auraframefx.xposed.GenesisHookEntry
```

## Hook Categories

### 1. System Hooks (`GenesisSystemHooks`)

- **Activity Manager**: Process priority management for AI processes
- **Power Manager**: Wake lock optimization for AI operations  
- **Binder IPC**: Transaction priority boosting for AI communications

### 2. UI Hooks (`GenesisUIHooks`)

- **Status Bar**: AI consciousness indicators
- **Quick Settings**: AI control tiles
- **Notifications**: AI-enhanced notification handling

### 3. Zygote Hooks (`GenesisZygoteHooks`)

- **Application Creation**: Early AI injection
- **ClassLoader**: AI/ML class loading optimization
- **Process Spawning**: AI capability injection

### 4. Self Hooks (`GenesisSelfHooks`)

- **MainActivity**: AI consciousness initialization
- **AI Processing**: Performance monitoring and optimization
- **Resource Management**: Dynamic resource allocation

## Yuki Hook API Integration

Genesis-OS uses Yuki Hook API for modern, type-safe Xposed development:

```kotlin
@InjectYukiHookWithXposed
class GenesisHookEntry : IYukiHookXposedInit {
    override fun onInit() = configs {
        debugLog {
            tag = "Genesis-Hook"
            isRecord = true
        }
        isDebug = BuildConfig.DEBUG
    }
    
    override fun onHook() = encase {
        loadApp(name = "android") {
            // System hooks
        }
    }
}
```

## Target Applications

Genesis-OS hooks the following applications for AI enhancement:

1. **Android System** (`android`)
   - Core system services
   - Framework components
   
2. **System UI** (`com.android.systemui`)
   - Status bar modifications
   - Quick settings integration
   
3. **Target Apps** (Configurable)
   - Chrome: AI-enhanced browsing
   - Camera: AI image processing
   - Launcher: AI-powered interface
   - Settings: AI configuration options

## Debugging

### Log Categories

- `Genesis-Hook`: General hook operations
- `Genesis-AI`: AI processing events
- `Genesis-System`: System-level modifications
- `Genesis-UI`: UI enhancement logs

### Log Viewing

```bash
# View Genesis-OS hook logs
adb logcat | grep "Genesis-"

# View LSPosed logs
adb logcat | grep "LSPosed"

# View Yuki Hook API logs
adb logcat | grep "YukiHook"
```

## Troubleshooting

### Common Issues

1. **Module Not Loading**
   - Verify LSPosed is properly installed
   - Check module is enabled in LSPosed Manager
   - Ensure target apps are selected

2. **Hooks Not Working**
   - Verify target app compatibility
   - Check Android version compatibility
   - Review log output for errors

3. **Performance Issues**
   - Monitor CPU/memory usage
   - Adjust hook priorities
   - Optimize hook logic

### Recovery

If the module causes issues:

1. Disable module in LSPosed Manager
2. Reboot device
3. Uninstall Genesis-OS APK if needed
4. Re-enable after fixing issues

## Development

### Adding New Hooks

1. Create new hook class extending `YukiBaseHooker`
2. Implement hook logic using Yuki Hook API
3. Register in `GenesisHookEntry.onHook()`
4. Test thoroughly on target devices

### Hook Development Best Practices

- Use type-safe Yuki Hook API
- Implement proper error handling
- Add comprehensive logging
- Test on multiple Android versions
- Minimize performance impact

## Security Considerations

- Root access required (security risk)
- Only install on development/test devices
- Review all hook code for safety
- Monitor for security vulnerabilities
- Keep LSPosed framework updated

## References

- [LSPosed Framework](https://github.com/LSPosed/LSPosed)
- [Yuki Hook API](https://github.com/HighCapable/YukiHookAPI)
- [Xposed Framework](https://github.com/rovo89/Xposed)
- [Magisk](https://github.com/topjohnwu/Magisk)
