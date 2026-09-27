@echo off
setlocal
set "MVNW_REPOURL=https://repo.maven.apache.org/maven2"
set "MVNW_JAR=%~dp0\.mvn\wrapper\maven-wrapper.jar"
if not exist "%MVNW_JAR%" (
  echo Maven wrapper jar not found at %MVNW_JAR% && exit /b 1
)
java -Dmaven.multiModuleProjectDirectory="%~dp0." -classpath "%MVNW_JAR%" org.apache.maven.wrapper.MavenWrapperMain %*
