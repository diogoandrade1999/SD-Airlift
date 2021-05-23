package plane;

/**
 * Plane Passenger Interface
 *
 * @author Diogo Andrade 89265
 * @author Rodrigo Oliveira 90514
 */
public interface PlanePassenger {
    /**
     * This method is used to passenger board the plane.
     * 
     * @param passengerId The passenger id.
     */
    void boardThePlane(int passengerId);

    /**
     * This method is used to passenger wait for end of flight.
     * 
     * @param passengerId The passenger id.
     */
    void waitForEndOfFlight(int passengerId);

    /**
     * This method is used to passenger leave the plane.
     * 
     * @param passengerId The passenger id.
     */
    void leaveThePlane(int passengerId);

}
