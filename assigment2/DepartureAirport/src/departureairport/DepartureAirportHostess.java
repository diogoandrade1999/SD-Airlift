package departureairport;

/**
 * Departure Airport Hostess Interface
 *
 * @author Diogo Andrade 89265
 * @author Rodrigo Oliveira 90514
 */
public interface DepartureAirportHostess {
    /**
     * This method is used to hostess wait for next passenger.
     * 
     * @return true if need to wait otherwise false
     */
    boolean waitForNextPassenger();

    /**
     * This method is used to hostess check documents.
     * 
     * @return The passenger id;
     */
    int checkDocuments();

}
