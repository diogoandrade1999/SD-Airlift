#!bin/bash

gnome-terminal -- /bin/sh -c 'cd Register/src; echo Registry; rmiregistry -J-Djava.rmi.server.useCodebaseOnly=false 5000' &
sleep 2;
gnome-terminal -- /bin/sh -c 'cd Register; bash ./run_localhost.sh 5001 localhost 5000' &
sleep 2;
gnome-terminal -- /bin/sh -c 'cd Repository; bash ./run_localhost.sh 5002 localhost 5000' &
sleep 2;
gnome-terminal -- /bin/sh -c 'cd DepartureAirport; bash ./run_localhost.sh 5003 localhost 5000' &
gnome-terminal -- /bin/sh -c 'cd DestinationAirport; bash ./run_localhost.sh 5004 localhost 5000' &
gnome-terminal -- /bin/sh -c 'cd Plane; bash ./run_localhost.sh 5005 localhost 5000' &
sleep 3;
gnome-terminal -- /bin/sh -c 'cd Pilot; echo Pilot; bash ./run_localhost.sh localhost 5000' &
gnome-terminal -- /bin/sh -c 'cd Hostess; echo Hostess; bash ./run_localhost.sh localhost 5000' &
gnome-terminal -- /bin/sh -c 'cd Passenger; echo Passengers; bash ./run_localhost.sh localhost 5000' &
sleep 30;
cat ./Repository/logging.log
