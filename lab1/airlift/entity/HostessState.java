package airlift.entity;

public enum HostessState {

    WAIT_FOR_NEXT_FLIGHT("WTFL"), WAIT_FOR_PASSENGER("WTPS"), CHECK_PASSENGER("CKPS"), READY_TO_FLY("RDTF");

    private String description;

    HostessState(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
