package departureairport;

/**
 * Departure Airport Passenger Interface
 *
 * @author Diogo Andrade 89265
 * @author Rodrigo Oliveira 90514
 */
public interface DepartureAirportPassenger {
    /**
     * This method is used to passenger wait in queue.
     * 
     * @param passengerId The passenger id.
     */
    void waitInQueue(int passengerId);

    /**
     * This method is used to passenger show the documents.
     * 
     * @param passengerId The passenger id.
     */
    void showDocuments(int passengerId);

}
