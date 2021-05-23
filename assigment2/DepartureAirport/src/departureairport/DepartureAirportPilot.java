package departureairport;

/**
 * Departure Airport Pilot Interface
 *
 * @author Diogo Andrade 89265
 * @author Rodrigo Oliveira 90514
 */
public interface DepartureAirportPilot {
    /**
     * This method is used to pilot park at transfer gate.
     * 
     * @return true if need to wait otherwise false
     */
    boolean parkAtTransferGate();

}
