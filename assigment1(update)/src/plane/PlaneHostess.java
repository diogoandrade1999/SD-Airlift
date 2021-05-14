package plane;

public interface PlaneHostess {

    boolean waitForNextFlight();

    void informPlaneReadyToTakeOff(int passengerId);

}
