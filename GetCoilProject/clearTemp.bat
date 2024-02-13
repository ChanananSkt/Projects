echo off
cls

echo.
echo "clear runtime"
call mvn clean

echo:
timeout 3
exit

