package airlift.plane;

public interface PlanePilot {

    void informPlaneReadyForBoarding();

    void waitForAllInBoard();

    void announceArrival();
}
