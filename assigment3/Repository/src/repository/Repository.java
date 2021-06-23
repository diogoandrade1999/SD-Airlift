package repository;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import entity.HostessState;
import entity.PassengerState;
import entity.PilotState;
import logger.AirliftLogger;
import main.Main;

/**
 * Repository
 *
 * @author Diogo Andrade 89265
 * @author Rodrigo Oliveira 90514
 */
public class Repository implements RepositoryInt {

    private ReentrantLock lock = new ReentrantLock(true);
    private AirliftLogger logger = new AirliftLogger();
    private List<Integer> flightsResume;
    private final int totalPassengers;
    private int numberPassengersInQueue;
    private int numberPassengersInPlane;
    private int numberPassengersInDestination;
    private int passengerInCheck;
    private PilotState pilotState;
    private HostessState hostessState;
    private PassengerState[] passengersState;

    /**
     * Creates an Repository.
     * 
     * @param totalPassengers The total of passangers.
     */
    public Repository(int totalPassengers) {
        // Global
        this.totalPassengers = totalPassengers;

        // Flights
        this.flightsResume = new ArrayList<>();

        // Queues
        this.numberPassengersInQueue = 0;
        this.numberPassengersInPlane = 0;
        this.numberPassengersInDestination = 0;

        // Entities State
        this.pilotState = PilotState.AT_TRANSFER_GATE;
        this.hostessState = HostessState.WAIT_FOR_NEXT_FLIGHT;
        this.passengersState = new PassengerState[totalPassengers];
        for (int i = 0; i < totalPassengers; i++) {
            this.passengersState[i] = PassengerState.GOING_TO_AIRPORT;
        }

        // initialLog
        this.logger.initialLog(totalPassengers);
        this.callStateLog();
    }

    /**
     * This method is used to update the Hostess State.
     * 
     * @param hostessState The Hostess State.
     */
    public void updateHostessState(HostessState hostessState) {
        this.lock.lock();
        try {
            this.hostessState = hostessState;
            if (hostessState == HostessState.WAIT_FOR_NEXT_FLIGHT) {
                if (this.flightsResume.size() > 0) {
                    this.callStateLog();
                }
            } else if (hostessState == HostessState.WAIT_FOR_PASSENGER) {
                this.callStateLog();
            } else if (hostessState == HostessState.CHECK_PASSENGER) {
                this.numberPassengersInQueue -= 1;
                this.logger.messageLog(this.flightsResume.size(), "passenger " + this.passengerInCheck + " checked.");
                this.callStateLog();
            } else if (hostessState == HostessState.READY_TO_FLY) {
                this.logger.messageLog(this.flightsResume.size(),
                        "departed with " + this.numberPassengersInPlane + " passengers.");
                this.callStateLog();
            }
        } finally {
            this.lock.unlock();
        }

    }

    /**
     * This method is used to update the Pilot State.
     * 
     * @param pilotState The Pilot State.
     */
    public void updatePilotState(PilotState pilotState) {
        this.lock.lock();
        try {
            this.pilotState = pilotState;
            if (pilotState == PilotState.AT_TRANSFER_GATE) {
                if (this.flightsResume.size() > 0) {
                    this.callStateLog();
                }
                if (this.totalPassengers == this.numberPassengersInDestination) {
                    this.logger.resumeLog(this.flightsResume);
                }
            } else if (pilotState == PilotState.READY_FOR_BOARDING) {
                this.flightsResume.add(0);
                this.logger.messageLog(this.flightsResume.size(), "boarding started.");
                this.callStateLog();
            } else if (pilotState == PilotState.WAIT_FOR_BOARDING) {
                this.callStateLog();
            } else if (pilotState == PilotState.FLYING_FORWARD) {
                this.callStateLog();
            } else if (pilotState == PilotState.DEBOARDING) {
                this.logger.messageLog(this.flightsResume.size(), "arrived.");
                this.callStateLog();
            } else if (pilotState == PilotState.FLYING_BACK) {
                this.logger.messageLog(this.flightsResume.size(), "returning.");
                this.callStateLog();
            }
        } finally {
            this.lock.unlock();
        }
    }

    /**
     * This method is used to update the Passenger State.
     * 
     * @param passengerState The Passenger State.
     * @param passengerId    The Passenger Id.
     */
    public void updatePassengerState(PassengerState passengerState, int passengerId) {
        this.lock.lock();
        try {
            this.passengersState[passengerId] = passengerState;
            if (passengerState == PassengerState.IN_QUEUE) {
                this.numberPassengersInQueue += 1;
                this.callStateLog();
            } else if (passengerState == PassengerState.IN_FLIGHT) {
                this.numberPassengersInPlane += 1;
                this.flightsResume.set(this.flightsResume.size() - 1, this.numberPassengersInPlane);
                this.callStateLog();
            } else if (passengerState == PassengerState.AT_DESTINATION) {
                this.numberPassengersInPlane -= 1;
                this.numberPassengersInDestination += 1;
                this.callStateLog();
            }
        } finally {
            this.lock.unlock();
        }
    }

    /**
     * This method is used to update the Id of Passenger in Check.
     * 
     * @param passengerInCheck The Passenger ID.
     */
    public void updatePassengerInCheck(int passengerInCheck) {
        this.passengerInCheck = passengerInCheck;
    }

    /**
     * This method is used to call the logger.
     */
    private void callStateLog() {
        this.logger.stateLog(this.pilotState, this.hostessState, this.passengersState, this.numberPassengersInQueue,
                this.numberPassengersInPlane, this.numberPassengersInDestination);
    }

    /**
     * This method is used to shutdown servers.
     * 
     * @throws RemoteException
     */
    @Override
    public void shutdown() throws RemoteException {
        this.lock.lock();
        try {
            Main.shutdown();
        } finally {
            this.lock.unlock();
        }
    }

}
