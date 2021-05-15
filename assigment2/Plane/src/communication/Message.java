package communication;

import java.io.Serializable;

import entity.HostessState;
import entity.PassengerState;
import entity.PilotState;

public class Message implements Serializable {

    private String msg;
    private String method;
    private int id;
    private boolean wait;
    private int numberPassengers;
    private PilotState pilotState;
    private HostessState hostessState;
    private PassengerState passengerState;

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMethod() {
        return this.method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isWait() {
        return this.wait;
    }

    public boolean getWait() {
        return this.wait;
    }

    public void setWait(boolean wait) {
        this.wait = wait;
    }

    public int getNumberPassengers() {
        return this.numberPassengers;
    }

    public void setNumberPassengers(int numberPassengers) {
        this.numberPassengers = numberPassengers;
    }

    public PilotState getPilotState() {
        return this.pilotState;
    }

    public void setPilotState(PilotState pilotState) {
        this.pilotState = pilotState;
    }

    public HostessState getHostessState() {
        return this.hostessState;
    }

    public void setHostessState(HostessState hostessState) {
        this.hostessState = hostessState;
    }

    public PassengerState getPassengerState() {
        return this.passengerState;
    }

    public void setPassengerState(PassengerState passengerState) {
        this.passengerState = passengerState;
    }

}
