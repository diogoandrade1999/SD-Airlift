#!bin/bash

javac ./src/communication/*.java ./src/entity/*.java ./src/main/*.java ./src/departureairport/*.java ./src/plane/*.java ./src/destinationairport/*.java

java -cp "./src/" main.Main $1 $2 $3 $4 $5 $6

rm ./src/*/*.class
