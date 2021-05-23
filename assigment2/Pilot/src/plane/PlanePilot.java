package plane;

/**
 * Plane Pilot Interface
 *
 * @author Diogo Andrade 89265
 * @author Rodrigo Oliveira 90514
 */
public interface PlanePilot {
    /**
     * This method is used to inform that the plane is ready for boarding.
     */
    void informPlaneReadyForBoarding();

    /**
     * This method is used to pilot wait for all in board.
     */
    void waitForAllInBoard();

    /**
     * This method is used to announce arrival.
     */
    void announceArrival();
}
