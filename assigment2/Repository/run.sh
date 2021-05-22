#!bin/bash

javac ./src/communication/*.java ./src/logger/*.java ./src/entity/*.java ./src/main/*.java ./src/repository/*.java

java -cp "./src/" main.Main $1 $2

rm ./src/*/*.class
