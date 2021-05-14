package communication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    private final int port;

    public Server(int port) {
        this.port = port;
    }

    public Socket start() {
        ServerSocket serverSocket;
        Socket clientSocket = null;
        try {
            serverSocket = new ServerSocket(this.port);
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, e);
        }
        return clientSocket;
    }
}
