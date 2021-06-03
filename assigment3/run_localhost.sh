#!bin/bash

gnome-terminal -- /bin/sh -c 'cd Register/src; echo Register; rmiregistry -J-Djava.rmi.server.useCodebaseOnly=false 5000' &
sleep 2;
gnome-terminal -- /bin/sh -c 'cd Register; bash ./run.sh 5001 localhost 5000' &
sleep 2;
gnome-terminal -- /bin/sh -c 'cd Repository; bash ./run.sh 5002 localhost 5000' &
sleep 2;
gnome-terminal -- /bin/sh -c 'cd DepartureAirport; bash ./run.sh 5003 localhost 5000' &
gnome-terminal -- /bin/sh -c 'cd DestinationAirport; bash ./run.sh 5004 localhost 5000' &
gnome-terminal -- /bin/sh -c 'cd Plane; bash ./run.sh 5005 localhost 5000' &
sleep 3;
gnome-terminal -- /bin/sh -c 'cd Pilot; echo Pilot; bash ./run.sh localhost 5000' &
gnome-terminal -- /bin/sh -c 'cd Hostess; echo Hostess; bash ./run.sh localhost 5000' &
gnome-terminal -- /bin/sh -c 'cd Passenger; echo Passenger; bash ./run.sh localhost 5000' &
sleep 30;
cat ./Repository/logging.log
