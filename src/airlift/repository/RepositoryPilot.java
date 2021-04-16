package airlift.repository;

import airlift.entity.PilotState;

public interface RepositoryPilot {

    void updatePilotState(PilotState pilotState);

}
