package communication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommunicationChannel implements Cloneable {

    private ServerSocket server;
    private Socket client;
    private String host;
    private int port;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public CommunicationChannel(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public Socket getClient() {
        return this.client;
    }

    public void start() {
        try {
            this.server = new ServerSocket(this.port);
            this.server.setSoTimeout(120000);
        } catch (IOException e) {
            Logger.getLogger(CommunicationChannel.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public CommunicationChannel accept() {
        try {
            this.client = this.server.accept();
        } catch (IOException e) {
            Logger.getLogger(CommunicationChannel.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
        CommunicationChannel channel;
        try {
            channel = (CommunicationChannel) this.clone();
        } catch (CloneNotSupportedException e) {
            Logger.getLogger(CommunicationChannel.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
        return channel;
    }

    public void end() {
        try {
            this.server.close();
        } catch (IOException e) {
            Logger.getLogger(CommunicationChannel.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void open() {
        try {
            this.client = new Socket(this.host, this.port);
        } catch (UnknownHostException e) {
            Logger.getLogger(CommunicationChannel.class.getName()).log(Level.SEVERE, null, e);
        } catch (IOException e) {
            Logger.getLogger(CommunicationChannel.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public Message readObject() {
        Message message = null;
        try {
            this.in = new ObjectInputStream(this.client.getInputStream());
            message = (Message) this.in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            Logger.getLogger(CommunicationChannel.class.getName()).log(Level.SEVERE, null, e);
        }
        return message;
    }

    public void writeObject(Message message) {
        try {
            this.out = new ObjectOutputStream(this.client.getOutputStream());
            this.out.writeObject(message);
            this.out.flush();
        } catch (IOException e) {
            Logger.getLogger(CommunicationChannel.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void close() {
        try {
            this.in.close();
            this.out.close();
            this.client.close();
        } catch (IOException e) {
            Logger.getLogger(CommunicationChannel.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
