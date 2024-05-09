echo building DMutexSimulator.jar as library ...
javac -d . ../src/*.java
jar cvef DMutexSimulator DMutexSimulator.jar src/*.class
rm ../src/*.class