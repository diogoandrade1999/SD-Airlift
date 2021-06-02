#!bin/bash

javac ./src/register/*.java ./src/departureairport/*.java ./src/entity/*.java ./src/main/*.java ./src/repository/*.java

java -Djava.security.policy=java.policy -cp "./src/" main.Main $1 $2 $3

rm ./src/*/*.class
