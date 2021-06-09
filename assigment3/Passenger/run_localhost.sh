#!bin/bash

javac ./src/entity/*.java ./src/main/*.java ./src/departureairport/*.java ./src/plane/*.java ./src/destinationairport/*.java

java -Djava.rmi.server.codebase="file:///home/diogo/Documents/4ano/sd/SD-Airlift/assigment3/Passenger/src/"\
     -Djava.rmi.server.useCodebaseOnly=false\
     -cp "./src/" main.Main $1 $2

rm ./src/*/*.class
