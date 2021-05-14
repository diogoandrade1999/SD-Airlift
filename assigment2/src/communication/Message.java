package communication;

import java.io.Serializable;

public class Message implements Serializable {

    private static final long serialVersionUID = 20140404L;

    private String msg;
    private String method;
    private String state;
    private int id;
    private boolean wait;
    private int numberPassengers;

    public Message() {
    }

    public Message(String method) {
        this.method = method;
    }

    public Message(String method, String state) {
        this.method = method;
        this.state = state;
    }

    public Message(String method, boolean wait) {
        this.method = method;
        this.wait = wait;
    }

    public Message(String method, String state, int id) {
        this.method = method;
        this.state = state;
        this.id = id;
    }

    public Message(String method, int id) {
        this.method = method;
        this.id = id;
    }

    public Message(int numberPassengers) {
        this.numberPassengers = numberPassengers;
    }

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

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
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
}
