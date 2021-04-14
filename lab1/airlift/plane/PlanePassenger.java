package airlift.plane;

public interface PlanePassenger {

    void boardThePlane(int passengerId);

    void waitForEndOfFlight(int passengerId);

    void leaveThePlane(int passengerId);

}
