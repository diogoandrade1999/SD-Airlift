package plane;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Plane implements PlanePilot, PlaneHostess, PlanePassenger {

    private final ReentrantLock lock = new ReentrantLock(true);
    private Queue<Integer> passengers = new LinkedList<>();
    private final Condition hostessWaitPlaneReadyToTakeOff = lock.newCondition();
    private final Condition pilotWaitForAllInBoard = lock.newCondition();
    private final Condition passengersWaitForEndOfFlight = lock.newCondition();
    private final Condition pilotWaitForDeboarding = lock.newCondition();
    private boolean planeReadyToTakeOff = false;
    private boolean planeReadyForBoarding = false;
    private boolean endOfFlight = false;
    private int passengerLeavePlane = -1;

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
            this.planeReadyToTakeOff = true;
            this.pilotWaitForAllInBoard.signal();
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public void informPlaneReadyForBoarding() {
        this.lock.lock();
        try {
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
    public void announceArrival() {
        this.lock.lock();
        try {
            this.endOfFlight = true;
            while (!this.passengers.isEmpty()) {
                this.passengerLeavePlane = this.passengers.poll();
                this.passengersWaitForEndOfFlight.signal();
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
    public void boardThePlane(int passengerId) {
        this.lock.lock();
        try {
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
            this.pilotWaitForDeboarding.signal();
        } finally {
            this.lock.unlock();
        }
    }
}
