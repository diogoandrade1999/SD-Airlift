package communication;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {

    private Socket socket;
    private ObjectOutputStream out;

    public Client(String host, int port, String name) {
        try {
            this.socket = new Socket(host, port);
            this.out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            Logger.getLogger(name).log(Level.SEVERE, null, e);
        }
    }

    public void send(Message message) {
        try {
            this.out.writeObject(message);
        } catch (IOException e) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
