#!bin/bash

javac ./src/communication/*.java ./src/destinationairport/*.java ./src/entity/*.java ./src/main/*.java ./src/repository/*.java

java -cp "./src/" main.Main $1 $2 $3 $4

rm ./src/*/*.class
