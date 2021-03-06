#!bin/bash

javac ./src/register/*.java ./src/main/*.java ./src/departureairport/*.java ./src/destinationairport/*.java ./src/plane/*.java ./src/repository/*.java ./src/entity/*.java

java -Djava.rmi.server.codebase="http://localhost/sd202/Register/src/"\
     -Djava.rmi.server.useCodebaseOnly=true\
     -Djava.security.policy=java.policy\
     -cp "./src/" main.Main $1 $2 $3

rm ./src/*/*.class
