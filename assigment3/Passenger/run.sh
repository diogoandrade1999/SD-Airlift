#!bin/bash

javac ./src/entity/*.java ./src/main/*.java ./src/departureairport/*.java ./src/plane/*.java ./src/destinationairport/*.java

java -cp "./src/" main.Main $1 $2

rm ./src/*/*.class
