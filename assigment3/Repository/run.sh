#!bin/bash

javac ./src/register/*.java ./src/logger/*.java ./src/entity/*.java ./src/main/*.java ./src/repository/*.java

java -Djava.rmi.server.codebase="http://localhost/sd202/Repository/src/"\
     -Djava.rmi.server.useCodebaseOnly=true\
     -Djava.security.policy=java.policy\
     -cp "./src/" main.Main $1 $2 $3

rm ./src/*/*.class
