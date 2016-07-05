package server;

import java.io.IOException;
import java.net.ServerSocket;

public class AcceptThread extends Thread {

    private ServerSocket socket;

    public AcceptThread(ServerSocket socket) {
        this.socket = socket;
    }

    public void run() {
        ServerWindow.log("Listening for clients...");
        while (!socket.isClosed()) {
            try {
                ConnectedClient client = new ConnectedClient(socket.accept());
                client.start();
                Server.INSTANCE.addClient(client);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}