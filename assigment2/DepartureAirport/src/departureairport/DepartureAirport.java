package departureairport;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import entity.PilotState;
import entity.HostessState;
import entity.PassengerState;
import repository.RepositoryInt;

public class DepartureAirport implements DepartureAirportPilot, DepartureAirportHostess, DepartureAirportPassenger {

    private RepositoryInt repository;
    private ReentrantLock lock = new ReentrantLock(true);
    private Queue<Integer> passengers = new LinkedList<>();
    private Condition passengerWaitInQueue = lock.newCondition();
    private Condition passengerWaitForHostessCheckDocuments = lock.newCondition();
    private Condition hostessWaitForNextPassenger = lock.newCondition();
    private Condition hostessWaitForPassengerShowDocuments = lock.newCondition();
    private boolean hostessCheckDocuments = false;
    private boolean passengerShowDocuments = false;
    private boolean passengerChecked = false;
    private int passengerInCheck = -1;
    private int lastPassengerInCheck = -1;
    private int numberPassengersChecked = 0;
    private int passengersStillMissing;
    private final int numberMaxPassengers;
    private final int numberMinPassengers;

    public DepartureAirport(RepositoryInt repository, int totalPassengers, int numberMaxPassengers,
            int numberMinPassengers) {
        this.repository = repository;
        this.passengersStillMissing = totalPassengers;
        this.numberMaxPassengers = numberMaxPassengers;
        this.numberMinPassengers = numberMinPassengers;
    }

    @Override
    public void waitInQueue(int passengerId) {
        this.lock.lock();
        try {
            // update passenger state
            this.repository.updatePassengerState(PassengerState.IN_QUEUE, passengerId);

            // add passenger to queue
            this.passengers.add(passengerId);

            // wake up hostess
            this.hostessWaitForNextPassenger.signal();

            // wait for check documents
            while (!this.hostessCheckDocuments
                    || (this.hostessCheckDocuments && passengerId != this.passengerInCheck)) {
                this.passengerWaitInQueue.await();
            }
            this.hostessCheckDocuments = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public void showDocuments(int passengerId) {
        this.lock.lock();
        try {
            // wake up hostess
            this.passengerShowDocuments = true;
            this.hostessWaitForPassengerShowDocuments.signal();

            // wait for hostess
            while (!this.passengerChecked || (this.passengerChecked && passengerId != this.lastPassengerInCheck)) {
                this.passengerWaitForHostessCheckDocuments.await();
            }
            this.passengerChecked = false;
            this.lastPassengerInCheck = -1;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public boolean waitForNextPassenger() {
        this.lock.lock();
        try {
            // update hostess state
            this.repository.updateHostessState(HostessState.WAIT_FOR_PASSENGER);

            if (this.passengerInCheck != -1) {
                this.lastPassengerInCheck = this.passengerInCheck;
                this.passengerInCheck = -1;
                this.passengerChecked = true;

                // wake up passengers
                this.passengerWaitForHostessCheckDocuments.signal();
                this.passengerWaitForHostessCheckDocuments.signalAll();
            }

            boolean firstCondition = (this.numberPassengersChecked < this.numberMinPassengers)
                    || (!this.passengers.isEmpty() && this.numberPassengersChecked < this.numberMaxPassengers);
            boolean secondCondition = this.passengersStillMissing != 0;
            boolean wait = (firstCondition && secondCondition);

            if (wait) {
                // Wait for passengers
                while (this.passengers.isEmpty()) {
                    this.hostessWaitForNextPassenger.await();
                }
                this.passengerInCheck = this.passengers.poll();
                this.passengersStillMissing--;
                this.numberPassengersChecked += 1;
            } else {
                this.numberPassengersChecked = 0;
            }

            return wait;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.lock.unlock();
        }
        return false;
    }

    @Override
    public int checkDocuments() {
        this.lock.lock();
        try {
            // update passenger id in check
            this.repository.updatePassengerInCheck(this.passengerInCheck);

            // update hostess state
            this.repository.updateHostessState(HostessState.CHECK_PASSENGER);

            // wake up passengers
            this.hostessCheckDocuments = true;
            this.passengerWaitInQueue.signal();
            this.passengerWaitInQueue.signalAll();

            // wait for passenger
            while (!this.passengerShowDocuments) {
                this.hostessWaitForPassengerShowDocuments.await();
            }
            this.passengerShowDocuments = false;

            return this.passengerInCheck;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.lock.unlock();
        }
        return -1;
    }

    @Override
    public boolean parkAtTransferGate() {
        this.lock.lock();
        try {
            // update pilot state
            this.repository.updatePilotState(PilotState.AT_TRANSFER_GATE);

            return this.passengersStillMissing != 0;
        } finally {
            this.lock.unlock();
        }
    }
}
