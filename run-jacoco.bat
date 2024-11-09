echo Running unit tests and generating code coverage...
mvn clean test jacoco:report

IF %ERRORLEVEL% EQU 0 (
    echo Unit tests and code coverage completed successfully!
) ELSE (
    echo Tests or coverage failed!
    exit /b 1
)