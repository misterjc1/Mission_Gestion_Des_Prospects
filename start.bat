@echo off
echo ========================================
echo   DEMARRAGE CYJE CRM
echo ========================================
echo.
echo [1/2] Demarrage PostgreSQL...
docker-compose up -d
timeout /t 10 /nobreak

echo.
echo [2/2] Demarrage Backend Spring Boot...
cd backend
start cmd /k "mvnw spring-boot:run"

echo.
echo ========================================
echo   SERVICES DEMARRES
echo ========================================
echo.
echo Backend: http://localhost:8080
echo Health: http://localhost:8080/actuator/health
echo.
pause
