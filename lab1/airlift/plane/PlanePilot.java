package airlift.plane;

public interface PlanePilot {

    void parkAtTransferGate(boolean park);

    void informPlaneReadyForBoarding();

    void waitForAllInBoard();

    void announceArrival();

    void flyToDestinationPoint(int flyingTime);

    void flyToDeparturePoint(int flyingTime);

}
