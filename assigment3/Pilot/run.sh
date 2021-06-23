#!bin/bash

javac ./src/entity/*.java ./src/main/*.java ./src/departureairport/*.java ./src/destinationairport/*.java ./src/plane/*.java

java -cp "./src/" main.Main $1 $2

rm ./src/*/*.class
