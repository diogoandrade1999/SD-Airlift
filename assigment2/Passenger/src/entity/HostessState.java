package entity;

/**
 * Hostess State
 *
 * @author Diogo Andrade 89265
 * @author Rodrigo Oliveira 90514
 */
public enum HostessState {

    WAIT_FOR_NEXT_FLIGHT("WTFL"), WAIT_FOR_PASSENGER("WTPS"), CHECK_PASSENGER("CKPS"), READY_TO_FLY("RDTF");

    private String description;

    /**
     * Creates an Hostess State.
     * 
     * @param description The description of Hostess State.
     */
    HostessState(String description) {
        this.description = description;
    }

    /**
     * This method is used to get the description of Hostess State.
     * 
     * @return The description of Hostess State.
     */
    public String getDescription() {
        return description;
    }
}
