package airlift.plane;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import airlift.main.AirliftLogger;

public class Plane implements PlanePilot, PlaneHostess, PlanePassenger {

    private AirliftLogger logger;
    private Queue<Integer> passengers = new LinkedList<>();
    private final ReentrantLock lock = new ReentrantLock(true);
    private final Condition hostessWaitPlaneReadyToTakeOff = lock.newCondition();
    private final Condition pilotWaitForAllInBoard = lock.newCondition();
    private final Condition passengersWaitForEndOfFlight = lock.newCondition();
    private final Condition pilotWaitForDeboarding = lock.newCondition();
    private boolean planeReadyToTakeOff = false;
    private boolean planeReadyForBoarding = false;
    private boolean endOfFlight = false;
    private int passengerLeavePlane = -1;
    private int flights = 0;

    public Plane(AirliftLogger airliftLogger) {
        this.logger = airliftLogger;
    }

    public int getFlights() {
        this.lock.lock();
        try {
            return this.flights;
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public int numberPassengersInPlane() {
        this.lock.lock();
        try {
            return this.passengers.size();
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public void waitForNextFlight() {
        this.lock.lock();
        try {
            this.logger.writeLog();

            while (!this.planeReadyForBoarding) {
                this.hostessWaitPlaneReadyToTakeOff.await();
            }
            this.planeReadyForBoarding = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public void informPlaneReadyToTakeOff() {
        this.lock.lock();
        try {
            this.logger.writeLog(0);

            this.planeReadyToTakeOff = true;
            this.pilotWaitForAllInBoard.signal();
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public void parkAtTransferGate(boolean park) {
        this.lock.lock();
        try {
            this.logger.writeLog();

            if (!park) {
                this.logger.writeLog(6);
            }
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public void informPlaneReadyForBoarding() {
        this.lock.lock();
        try {
            this.flights += 1;

            this.logger.writeLog(1);

            this.planeReadyForBoarding = true;
            this.hostessWaitPlaneReadyToTakeOff.signal();
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public void waitForAllInBoard() {
        this.lock.lock();
        try {
            this.logger.writeLog();

            while (!this.planeReadyToTakeOff) {
                this.pilotWaitForAllInBoard.await();
            }
            this.planeReadyToTakeOff = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public void flyToDestinationPoint(int flyingTime) {
        this.lock.lock();
        try {
            this.logger.writeLog();

            Thread.sleep(flyingTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public void announceArrival() {
        this.lock.lock();
        try {
            this.logger.writeLog(2);

            this.endOfFlight = true;
            while (!this.passengers.isEmpty()) {
                this.passengerLeavePlane = this.passengers.poll();
                this.passengersWaitForEndOfFlight.signalAll();
                this.pilotWaitForDeboarding.await();
            }
            this.endOfFlight = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public void flyToDeparturePoint(int flyingTime) {
        this.lock.lock();
        try {
            this.logger.writeLog(3);

            Thread.sleep(flyingTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public void boardThePlane(int passengerId) {
        this.lock.lock();
        try {
            this.logger.writeLog();
            this.passengers.add(passengerId);
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public void waitForEndOfFlight(int passengerId) {
        this.lock.lock();
        try {
            while (!this.endOfFlight || (this.endOfFlight && passengerId != this.passengerLeavePlane)) {
                this.passengersWaitForEndOfFlight.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public void leaveThePlane(int passengerId) {
        this.lock.lock();
        try {
            this.passengers.remove(passengerId);
            this.logger.writeLog();
            this.pilotWaitForDeboarding.signal();
        } finally {
            this.lock.unlock();
        }
    }
}
