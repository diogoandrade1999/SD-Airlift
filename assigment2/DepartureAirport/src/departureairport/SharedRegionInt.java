package departureairport;

import communication.Message;

/**
 * Shared Region Interface
 *
 * @author Diogo Andrade 89265
 * @author Rodrigo Oliveira 90514
 */
public class SharedRegionInt {

    private DepartureAirport departureAirport;

    /**
     * Creates an Shared Region Interface.
     * 
     * @param departureAirport The Departure Airport.
     */
    public SharedRegionInt(DepartureAirport departureAirport) {
        this.departureAirport = departureAirport;
    }

    /**
     * This method is used to process and reply the input message.
     * 
     * @param message The input message.
     * @return The output message.
     */
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
