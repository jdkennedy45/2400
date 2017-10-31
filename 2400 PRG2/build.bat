@echo off

cd src

del *.class
javac *.java
java Driver ../%1 ../%2 %3 %4
cd ..