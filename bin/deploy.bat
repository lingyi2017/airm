@echo off
echo [INFO] deploy the war in target dir.

cd %~dp0
cd ..
mvn clean deploy -Dmaven.test.skip=true
cd bin
pause