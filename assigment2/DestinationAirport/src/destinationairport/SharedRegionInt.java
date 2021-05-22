package destinationairport;

import communication.Message;

public class SharedRegionInt {

    private DestinationAirport destinationAirport;

    public SharedRegionInt(DestinationAirport destinationAirport) {
        this.destinationAirport = destinationAirport;
    }

    public Message processAndReply(Message message) {
        Message messageOut = new Message();
        if (message.getMethod().equals("atDestination")) {
            this.destinationAirport.atDestination(message.getId());
        }
        return messageOut;

    }
}
