package airlift.entity;

public enum PassengerState {

    GOING_TO_AIRPORT("GTAP"), IN_QUEUE("INQE"), IN_FLIGHT("INFL"), AT_DESTINATION("ATDS");

    private String description;

    PassengerState(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
