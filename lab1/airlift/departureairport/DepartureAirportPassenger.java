package airlift.departureairport;

public interface DepartureAirportPassenger {

    void waitInQueue(int passengerId);

    void showDocuments(int passengerId);

}
