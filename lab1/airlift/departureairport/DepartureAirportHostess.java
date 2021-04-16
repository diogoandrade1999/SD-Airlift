package airlift.departureairport;

public interface DepartureAirportHostess {

    int numberPassengersInQueue();

    void waitForNextPassenger(boolean wait);

    void checkDocuments();

    int getPassengerInCheck();

    int numberPassengersChecked();

}
