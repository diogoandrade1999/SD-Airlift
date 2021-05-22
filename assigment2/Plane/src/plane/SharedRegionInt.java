package plane;

import communication.Message;

public class SharedRegionInt {

    private Plane plane;

    public SharedRegionInt(Plane plane) {
        this.plane = plane;
    }

    public Message processAndReply(Message message) {
        Message messageOut = new Message();
        if (message.getMethod().equals("waitForNextFlight")) {
            boolean wait = this.plane.waitForNextFlight();
            messageOut.setWait(wait);
        } else if (message.getMethod().equals("informPlaneReadyToTakeOff")) {
            this.plane.informPlaneReadyToTakeOff(message.getId());
        } else if (message.getMethod().equals("informPlaneReadyForBoarding")) {
            this.plane.informPlaneReadyForBoarding();
        } else if (message.getMethod().equals("waitForAllInBoard")) {
            this.plane.waitForAllInBoard();
        } else if (message.getMethod().equals("announceArrival")) {
            this.plane.announceArrival();
        } else if (message.getMethod().equals("boardThePlane")) {
            this.plane.boardThePlane(message.getId());
        } else if (message.getMethod().equals("waitForEndOfFlight")) {
            this.plane.waitForEndOfFlight(message.getId());
        } else if (message.getMethod().equals("leaveThePlane")) {
            this.plane.leaveThePlane(message.getId());
        }
        return messageOut;

    }
}
