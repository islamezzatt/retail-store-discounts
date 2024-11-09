echo Building the project...
mvn clean package -DskipTests


IF %ERRORLEVEL% EQU 0 (
    echo Build successful!
) ELSE (
    echo Build failed!
    exit /b 1
)