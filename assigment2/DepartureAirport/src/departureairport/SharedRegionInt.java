package departureairport;

import communication.Message;

public class SharedRegionInt {

    private DepartureAirport departureAirport;

    public SharedRegionInt(DepartureAirport departureAirport) {
        this.departureAirport = departureAirport;
    }

    public Message processAndReply(Message message) {
        Message messageOut = new Message();
        if (message.getMethod().equals("waitInQueue")) {
            this.departureAirport.waitInQueue(message.getId());
        } else if (message.getMethod().equals("showDocuments")) {
            this.departureAirport.showDocuments(message.getId());
        } else if (message.getMethod().equals("waitForNextPassenger")) {
            boolean wait = this.departureAirport.waitForNextPassenger();
            messageOut.setWait(wait);
        } else if (message.getMethod().equals("checkDocuments")) {
            int id = this.departureAirport.checkDocuments();
            messageOut.setId(id);
        } else if (message.getMethod().equals("parkAtTransferGate")) {
            boolean wait = this.departureAirport.parkAtTransferGate();
            messageOut.setWait(wait);
        }
        return messageOut;

    }
}
