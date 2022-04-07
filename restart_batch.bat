@echo off

echo --RESTART BATCH--
set "TOMCAT_PATH=C:\apache_tomcat_1"
set "STOP=%TOMCAT_PATH%\bin\shutdown.bat"
set "START=%TOMCAT_PATH%\bin\startup.bat"

@echo on
call %STOP%
timeout /t 2
call %START%
timeout /t 2