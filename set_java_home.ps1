# Set JAVA_HOME to Java 24 installation
$javaHome = "C:\Android Tools\zulu24.32.13-ca-jdk24.0.2-win_x64"

# Set JAVA_HOME for the current session
[System.Environment]::SetEnvironmentVariable("JAVA_HOME", $javaHome, [System.EnvironmentVariableTarget]::Process)

# Verify Java installation
Write-Host "JAVA_HOME set to: $env:JAVA_HOME"
Write-Host "Java version:"
& "$javaHome\bin\java" -version

# Run Gradle wrapper with the updated environment
Write-Host "`nRunning Gradle wrapper..."
.\gradlew.bat --version
