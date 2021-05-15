#!bin/bash

#javac ../DepartureAirport/src/communication/*.java ../DepartureAirport/src/departureairport/*.java ../DepartureAirport/src/entity/*.java ../DepartureAirport/src/main/*.java ../DepartureAirport/src/repository/*.java
#javac ../DestinationAirport/src/communication/*.java ../DestinationAirport/src/destinationairport/*.java ../DestinationAirport/src/entity/*.java ../DestinationAirport/src/main/*.java ../DestinationAirport/src/repository/*.java
#javac ../Plane/src/communication/*.java ../Plane/src/plane/*.java ../Plane/src/entity/*.java ../Plane/src/main/*.java ../Plane/src/repository/*.java
#javac ../Repository/src/communication/*.java ../Repository/src/logger/*.java ../Repository/src/entity/*.java ../Repository/src/main/*.java ../Repository/src/repository/*.java
#javac ../Pilot/src/communication/*.java ../Pilot/src/entity/*.java ../Pilot/src/main/*.java ../Pilot/src/departureairport/*.java ../Pilot/src/plane/*.java
#javac ../Hostess/src/communication/*.java ../Hostess/src/entity/*.java ../Hostess/src/main/*.java ../Hostess/src/departureairport/*.java ../Hostess/src/plane/*.java
#javac ../Passenger/src/communication/*.java ../Passenger/src/entity/*.java ../Passenger/src/main/*.java ../Passenger/src/departureairport/*.java ../Passenger/src/plane/*.java ../Passenger/src/destinationairport/*.java

#java -cp "../DepartureAirport/src/" main.Main
#java -cp "../DestinationAirport/src/" main.Main
#java -cp "../Plane/src/" main.Main
#java -cp "../Repository/src/" main.Main
#java -cp "../Pilot/src/" main.Main
#java -cp "../Hostess/src/" main.Main
#java -cp "../Passenger/src/" main.Main

rm ../DepartureAirport/src/*/*.class
rm ../DestinationAirport/src/*/*.class
rm ../Plane/src/*/*.class
rm ../Repository/src/*/*.class
rm ../Pilot/src/*/*.class
rm ../Hostess/src/*/*.class
rm ../Passenger/src/*/*.class