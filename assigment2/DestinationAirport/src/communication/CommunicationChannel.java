package communication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommunicationChannel {

    private ServerSocket server;
    private Socket client;
    private final int port;
    private final String host = "localhost";

    public CommunicationChannel(int port) {
        this.port = port;
    }

    public Socket getClient() {
        return this.client;
    }

    public void start() {
        try {
            this.server = new ServerSocket(this.port);
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
        return this;
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
        ObjectInputStream in;
        try {
            in = new ObjectInputStream(this.client.getInputStream());
            message = (Message) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            Logger.getLogger(CommunicationChannel.class.getName()).log(Level.SEVERE, null, e);
        }
        return message;
    }

    public void writeObject(Message message) {
        ObjectOutputStream out;
        try {
            out = new ObjectOutputStream(this.client.getOutputStream());
            out.writeObject(message);
        } catch (IOException e) {
            Logger.getLogger(CommunicationChannel.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void close() {
        try {
            this.client.close();
        } catch (IOException e) {
            Logger.getLogger(CommunicationChannel.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
