package entity;

/**
 * Passenger State
 *
 * @author Diogo Andrade 89265
 * @author Rodrigo Oliveira 90514
 */
public enum PassengerState {

    GOING_TO_AIRPORT("GTAP"), IN_QUEUE("INQE"), IN_FLIGHT("INFL"), AT_DESTINATION("ATDS");

    private String description;

    /**
     * Creates an Passenger State.
     * 
     * @param description The description of Passenger State.
     */
    PassengerState(String description) {
        this.description = description;
    }

    /**
     * This method is used to get the description of Passenger State.
     * 
     * @return The description of Passenger State.
     */
    public String getDescription() {
        return description;
    }
}
