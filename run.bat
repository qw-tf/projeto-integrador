@echo off
chcp 65001 > nul

echo ======================================
echo COMPILANDO TODOS OS ARQUIVOS .JAVA...
echo ======================================

rem Apaga arquivos antigos .class
del /s /q "Bin\*.class" >nul 2>&1

rem Compila todos os arquivos .java
for /R %%f in (*.java) do (
    echo Compilando "%%f"
    javac -encoding UTF-8 -d "Bin" "%%f"
)

if errorlevel 1 (
    echo.
    echo ERRO NA COMPILACAO. Corrija os erros acima.
    pause
    exit /b
)

echo.
echo =======================
echo EXECUTANDO O PROGRAMA...
echo =======================
java -cp "Bin;lib\postgresql-42.7.6.jar" Interfaces.SistemaPrincipal
pause