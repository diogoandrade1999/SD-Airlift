package entity;

public enum PilotState {

    AT_TRANSFER_GATE("ATRG"), READY_FOR_BOARDING("RDFB"), WAIT_FOR_BOARDING("WTFB"), FLYING_FORWARD("FLFW"),
    DEBOARDING("DRPP"), FLYING_BACK("FLBK");

    private String description;

    PilotState(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
