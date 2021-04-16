package airlift.departureairport;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class DepartureAirport implements DepartureAirportHostess, DepartureAirportPassenger {

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

    @Override
    public int numberPassengersInQueue() {
        this.lock.lock();
        try {
            return this.passengers.size();
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public int numberPassengersChecked() {
        this.lock.lock();
        try {
            int numberPassengersChecked = this.numberPassengersChecked;
            if (this.passengerInCheck != -1) {
                numberPassengersChecked += 1;
            }
            return numberPassengersChecked;
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public void waitInQueue(int passengerId) {
        this.lock.lock();
        try {
            this.passengers.add(passengerId);
            this.hostessWaitForNextPassenger.signal();

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
            this.passengerShowDocuments = true;
            this.hostessWaitForPassengerShowDocuments.signal();

            while (!this.passengerChecked || (this.passengerChecked && passengerId != this.lastPassengerInCheck)) {
                this.passengerWaitForHostessCheckDocuments.await();
            }
            this.passengerChecked = false;
            this.lastPassengerInCheck = -1;
            this.numberPassengersChecked += 1;
            this.hostessWaitForNextPassenger.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public int getPassengerInCheck() {
        this.lock.lock();
        try {
            return this.passengerInCheck;
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public void waitForNextPassenger(boolean wait) {
        this.lock.lock();
        try {
            if (this.passengerInCheck != -1) {
                this.lastPassengerInCheck = this.passengerInCheck;
                this.passengerInCheck = -1;
                this.passengerChecked = true;
                this.passengerWaitForHostessCheckDocuments.signal();
                this.passengerWaitForHostessCheckDocuments.signalAll();
            }

            if (wait) {
                while (this.passengers.isEmpty()) {
                    this.hostessWaitForNextPassenger.await();
                }
                this.passengerInCheck = this.passengers.poll();
            } else {
                while (this.lastPassengerInCheck != -1) {
                    this.hostessWaitForNextPassenger.await();
                }
                this.numberPassengersChecked = 0;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public void checkDocuments() {
        this.lock.lock();
        try {
            this.hostessCheckDocuments = true;
            this.passengerWaitInQueue.signal();
            this.passengerWaitInQueue.signalAll();

            while (!this.passengerShowDocuments) {
                this.hostessWaitForPassengerShowDocuments.await();
            }
            this.passengerShowDocuments = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.lock.unlock();
        }
    }
}
