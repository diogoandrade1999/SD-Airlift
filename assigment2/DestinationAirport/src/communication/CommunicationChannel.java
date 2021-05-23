package communication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Communication Channel
 *
 * @author Diogo Andrade 89265
 * @author Rodrigo Oliveira 90514
 * @see Cloneable
 */
public class CommunicationChannel implements Cloneable {

    private ServerSocket server;
    private Socket client;
    private String host;
    private int port;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    /**
     * This method is used to clone the Communication Channel.
     *
     * @return This returns the clone of the Communication Channel.
     * @exception CloneNotSupportedException
     */
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * Creates an Communication Channel.
     * 
     * @param host The server host.
     * @param port The server port.
     */
    public CommunicationChannel(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * This method is used to start the server.
     * 
     * @exception IOException
     */
    public void start() {
        try {
            this.server = new ServerSocket(this.port);
            this.server.setSoTimeout(15000);
        } catch (IOException e) {
            Logger.getLogger(CommunicationChannel.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * This method is used to accept the communication to the server from client.
     * 
     * @return The clone of the Communication Channel.
     * @exception IOException
     * @exception CloneNotSupportedException
     */
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

    /**
     * This method is used to close the server.
     * 
     * @exception IOException
     */
    public void end() {
        try {
            this.server.close();
        } catch (IOException e) {
            Logger.getLogger(CommunicationChannel.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * This method is used to open the communication from client to the server.
     * 
     * @exception UnknownHostException
     * @exception IOException
     */
    public void open() {
        try {
            this.client = new Socket(this.host, this.port);
        } catch (UnknownHostException e) {
            Logger.getLogger(CommunicationChannel.class.getName()).log(Level.SEVERE, null, e);
        } catch (IOException e) {
            Logger.getLogger(CommunicationChannel.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * This method is used to receive and read the input message.
     * 
     * @return The input message.
     * @exception IOException
     * @exception ClassNotFoundException
     */
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

    /**
     * This method is used to send the output message.
     * 
     * @param message The output message.
     * @exception IOException
     */
    public void writeObject(Message message) {
        try {
            this.out = new ObjectOutputStream(this.client.getOutputStream());
            this.out.writeObject(message);
            this.out.flush();
        } catch (IOException e) {
            Logger.getLogger(CommunicationChannel.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * This method is used to close the communication from client to the server.
     * 
     * @exception IOException
     */
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
