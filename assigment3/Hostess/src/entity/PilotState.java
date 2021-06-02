package entity;

/**
 * Pilot State
 *
 * @author Diogo Andrade 89265
 * @author Rodrigo Oliveira 90514
 */
public enum PilotState {

    AT_TRANSFER_GATE("ATRG"), READY_FOR_BOARDING("RDFB"), WAIT_FOR_BOARDING("WTFB"), FLYING_FORWARD("FLFW"),
    DEBOARDING("DRPP"), FLYING_BACK("FLBK");

    private String description;

    /**
     * Creates an Pilot State.
     * 
     * @param description The description of Pilot State.
     */
    PilotState(String description) {
        this.description = description;
    }

    /**
     * This method is used to get the description of Pilot State.
     * 
     * @return The description of Pilot State.
     */
    public String getDescription() {
        return description;
    }
}
