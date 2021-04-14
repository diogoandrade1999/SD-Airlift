package airlift.departureairport;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import airlift.main.AirliftLogger;

public class DepartureAirport implements DepartureAirportHostess, DepartureAirportPassenger {

    private AirliftLogger logger;
    private Queue<Integer> passengers = new LinkedList<>();
    private ReentrantLock lock = new ReentrantLock(true);
    private Condition passengerWaitInQueue = lock.newCondition();
    private Condition passengerWaitForHostessCheckDocuments = lock.newCondition();
    private Condition hostessWaitForNextPassenger = lock.newCondition();
    private Condition hostessWaitForPassengerShowDocuments = lock.newCondition();
    private boolean hostessCheckDocuments = false;
    private boolean passengerShowDocuments = false;
    private boolean passengerChecked = false;
    private int passengerInCheck = -1;
    private int lastPassengerInCheck = -1;

    public DepartureAirport(AirliftLogger airliftLogger) {
        this.logger = airliftLogger;
    }

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
    public void waitInQueue(int passengerId) {
        this.lock.lock();
        try {
            this.passengers.add(passengerId);
            this.logger.writeLog();
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
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public int inCheck() {
        this.lock.lock();
        try {
            if (this.passengerInCheck != 0) {
                return 1;
            }
        } finally {
            this.lock.unlock();
        }
        return 0;
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
            this.logger.writeLog();

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
                this.hostessCheckDocuments = true;
                this.passengerInCheck = this.passengers.poll();
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
            this.logger.writeLog(5);

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
