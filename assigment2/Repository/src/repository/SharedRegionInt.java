package repository;

import communication.Message;

/**
 * Shared Region Interface
 *
 * @author Diogo Andrade 89265
 * @author Rodrigo Oliveira 90514
 */
public class SharedRegionInt {

    private Repository repository;

    /**
     * Creates an Shared Region Interface.
     * 
     * @param repository The repository.
     */
    public SharedRegionInt(Repository repository) {
        this.repository = repository;
    }

    /**
     * This method is used to process and reply the input message.
     * 
     * @param message The input message.
     * @return The output message.
     */
    public Message processAndReply(Message message) {
        Message messageOut = new Message();
        if (message.getMethod().equals("updateHostessState")) {
            this.repository.updateHostessState(message.getHostessState());
        } else if (message.getMethod().equals("updatePilotState")) {
            this.repository.updatePilotState(message.getPilotState());
        } else if (message.getMethod().equals("updatePassengerState")) {
            this.repository.updatePassengerState(message.getPassengerState(), message.getId());
        } else if (message.getMethod().equals("updatePassengerInCheck")) {
            this.repository.updatePassengerInCheck(message.getId());
        }
        return messageOut;

    }
}
