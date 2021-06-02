#!bin/bash

javac ./src/register/*.java ./src/main/*.java

java -Djava.security.policy=java.policy -cp "./src/" main.Main $1 $2 $3

rm ./src/*/*.class
