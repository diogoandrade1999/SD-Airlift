package plane;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import entity.HostessState;
import entity.PassengerState;
import entity.PilotState;
import repository.RepositoryInt;

/**
 * Plane
 *
 * @author Diogo Andrade 89265
 * @author Rodrigo Oliveira 90514
 */
public class Plane implements PlaneInt {

    private RepositoryInt repository;
    private final ReentrantLock lock = new ReentrantLock(true);
    private Queue<Integer> passengers = new LinkedList<>();
    private final Condition hostessWaitPlaneReadyToTakeOff = lock.newCondition();
    private final Condition hostessWaitForLastPassengerBoard = lock.newCondition();
    private final Condition pilotWaitForAllInBoard = lock.newCondition();
    private final Condition passengersWaitForEndOfFlight = lock.newCondition();
    private final Condition pilotWaitForDeboarding = lock.newCondition();
    private boolean planeReadyToTakeOff = false;
    private boolean planeReadyForBoarding = false;
    private boolean endOfFlight = false;
    private int passengerLeavePlane = -1;
    private int passengersStillMissing;
    private int lastPassengerToBoard = -1;

    /**
     * Creates an Plane.
     * 
     * @param repository      The repository.
     * @param totalPassengers The total passengers.
     */
    public Plane(RepositoryInt repository, int totalPassengers) {
        this.repository = repository;
        this.passengersStillMissing = totalPassengers;
    }

    /**
     * This method is used to hostess wait for next flight.
     * 
     * @throws RemoteException
     */
    @Override
    public boolean waitForNextFlight() {
        this.lock.lock();
        try {
            boolean wait = this.passengersStillMissing != 0;

            // update hostess state
            this.repository.updateHostessState(HostessState.WAIT_FOR_NEXT_FLIGHT);

            if (wait) {
                // wait for pilot
                while (!this.planeReadyForBoarding) {
                    this.hostessWaitPlaneReadyToTakeOff.await();
                }
                this.planeReadyForBoarding = false;
            }

            return wait;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } finally {
            this.lock.unlock();
        }
        return false;
    }

    /**
     * This method is used to inform plane ready to take off.
     * 
     * @param passengerId The id of last passenger.
     * @throws RemoteException
     */
    @Override
    public void informPlaneReadyToTakeOff(int passengerId) {
        this.lock.lock();
        try {
            // wait for last passenger
            while (this.lastPassengerToBoard != passengerId) {
                this.hostessWaitForLastPassengerBoard.await();
            }

            // update hostess state
            this.repository.updateHostessState(HostessState.READY_TO_FLY);

            // wake up pilot
            this.planeReadyToTakeOff = true;
            this.pilotWaitForAllInBoard.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } finally {
            this.lock.unlock();
        }
    }

    /**
     * This method is used to inform that the plane is ready for boarding.
     * 
     * @throws RemoteException
     */
    @Override
    public void informPlaneReadyForBoarding() {
        this.lock.lock();
        try {
            // update pilot state
            this.repository.updatePilotState(PilotState.READY_FOR_BOARDING);

            // wake up hostess
            this.planeReadyForBoarding = true;
            this.hostessWaitPlaneReadyToTakeOff.signal();
        } catch (RemoteException e) {
            e.printStackTrace();
        } finally {
            this.lock.unlock();
        }
    }

    /**
     * This method is used to pilot wait for all in board.
     * 
     * @throws RemoteException
     */
    @Override
    public void waitForAllInBoard() {
        this.lock.lock();
        try {
            // update pilot state
            this.repository.updatePilotState(PilotState.WAIT_FOR_BOARDING);

            // wait for all passengers
            while (!this.planeReadyToTakeOff) {
                this.pilotWaitForAllInBoard.await();
            }
            this.planeReadyToTakeOff = false;

            // update pilot state
            this.repository.updatePilotState(PilotState.FLYING_FORWARD);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } finally {
            this.lock.unlock();
        }
    }

    /**
     * This method is used to announce arrival.
     * 
     * @throws RemoteException
     */
    @Override
    public void announceArrival() {
        this.lock.lock();
        try {
            // update pilot state
            this.repository.updatePilotState(PilotState.DEBOARDING);

            // call passengers
            this.endOfFlight = true;
            while (!this.passengers.isEmpty()) {
                // wake up passengers
                this.passengerLeavePlane = this.passengers.poll();
                this.passengersWaitForEndOfFlight.signal();
                this.passengersWaitForEndOfFlight.signalAll();

                // wait for all passengers
                this.pilotWaitForDeboarding.await();
            }
            this.endOfFlight = false;

            // update pilot state
            this.repository.updatePilotState(PilotState.FLYING_BACK);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } finally {
            this.lock.unlock();
        }
    }

    /**
     * This method is used to passenger board the plane.
     * 
     * @param passengerId The passenger id.
     * @throws RemoteException
     */
    @Override
    public void boardThePlane(int passengerId) {
        this.lock.lock();
        try {
            // update passenger state
            this.repository.updatePassengerState(PassengerState.IN_FLIGHT, passengerId);

            // add passenger to queue
            this.passengers.add(passengerId);
            this.passengersStillMissing--;
            this.lastPassengerToBoard = passengerId;

            // wake up hostess
            this.hostessWaitForLastPassengerBoard.signal();
        } catch (RemoteException e) {
            e.printStackTrace();
        } finally {
            this.lock.unlock();
        }
    }

    /**
     * This method is used to passenger wait for end of flight.
     * 
     * @param passengerId The passenger id.
     */
    @Override
    public void waitForEndOfFlight(int passengerId) {
        this.lock.lock();
        try {
            // wait for pilot
            while (!this.endOfFlight || (this.endOfFlight && passengerId != this.passengerLeavePlane)) {
                this.passengersWaitForEndOfFlight.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.lock.unlock();
        }
    }

    /**
     * This method is used to passenger leave the plane.
     * 
     * @param passengerId The passenger id.
     */
    @Override
    public void leaveThePlane(int passengerId) {
        this.lock.lock();
        try {
            // wake up pilot
            this.pilotWaitForDeboarding.signal();
        } finally {
            this.lock.unlock();
        }
    }
}
