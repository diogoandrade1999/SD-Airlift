package plane;

/**
 * Plane Hostess Interface
 *
 * @author Diogo Andrade 89265
 * @author Rodrigo Oliveira 90514
 */
public interface PlaneHostess {
    /**
     * This method is used to hostess wait for next flight.
     */
    boolean waitForNextFlight();

    /**
     * This method is used to inform plane ready to take off.
     * 
     * @param passengerId The id of last passenger.
     */
    void informPlaneReadyToTakeOff(int passengerId);

}
