package destinationairport;

import communication.Message;

/**
 * Shared Region Interface
 *
 * @author Diogo Andrade 89265
 * @author Rodrigo Oliveira 90514
 */
public class SharedRegionInt {

    private DestinationAirport destinationAirport;

    /**
     * Creates an Shared Region Interface.
     * 
     * @param destinationAirport The Destination Airport.
     */
    public SharedRegionInt(DestinationAirport destinationAirport) {
        this.destinationAirport = destinationAirport;
    }

    /**
     * This method is used to process and reply the input message.
     * 
     * @param message The input message.
     * @return The output message.
     */
    public Message processAndReply(Message message) {
        Message messageOut = new Message();
        if (message.getMethod().equals("atDestination")) {
            this.destinationAirport.atDestination(message.getId());
        }
        return messageOut;

    }
}
