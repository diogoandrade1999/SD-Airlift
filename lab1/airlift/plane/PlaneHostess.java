package airlift.plane;

public interface PlaneHostess {

    int numberPassengersInPlane();

    void waitForNextFlight();

    void informPlaneReadyToTakeOff();

}
