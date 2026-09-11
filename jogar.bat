@echo off
cd /d "%~dp0"

if not exist bin mkdir bin

echo Compilando...
javac -d bin src\*.java
if errorlevel 1 (
    echo.
    echo Erro na compilacao. Confira as mensagens acima.
    pause
    exit /b
)

java -cp bin App
pause
