#!bin/bash

javac ./src/register/*.java ./src/plane/*.java ./src/entity/*.java ./src/main/*.java ./src/repository/*.java

java -Djava.rmi.server.codebase="file:///home/diogo/Documents/4ano/sd/SD-Airlift/assigment3/Plane/src/"\
     -Djava.rmi.server.useCodebaseOnly=false\
     -Djava.security.policy=java.policy\
     -cp "./src/" main.Main $1 $2 $3

rm ./src/*/*.class
