@echo off
echo building DMutexSimulator.jar as library ...
javac -d . ../src/*.java
jar cvef Main DMutexSimulator.jar src/*.class
del /f ..\build\src\*.class