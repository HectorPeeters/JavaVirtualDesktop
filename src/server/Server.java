package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

public class Server {

    public List<ConnectedClient> connectedClients = new ArrayList<>();

    private ServerSocket socket;

    public static Server INSTANCE;

    public Server(int port) {
        INSTANCE = this;
        try {
            socket = new ServerSocket(port);
            socket.setSoTimeout(1000000);
            ServerWindow.log("Server socket initialized on port " + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        new AcceptThread(socket).start();
    }

    public void addClient(ConnectedClient client) {
        connectedClients.add(client);
        ServerWindow.log("Connected client " + client.getRemoteSocketAddress());
        ServerWindow.getInstance().updateView();
    }

    public void removeClient(ConnectedClient client) {
        connectedClients.remove(client);
        ServerWindow.log("Disconnected from client " + client.getRemoteSocketAddress());
        ServerWindow.getInstance().updateView();
    }

    public static void main(String[] args) {
        new ServerWindow();
        new Server(Integer.parseInt(args[0]));
    }

    public void removeClient(SocketAddress remoteSocketAddress) {
        for(ConnectedClient client : connectedClients) {
            if(client.getRemoteSocketAddress().equals(remoteSocketAddress)) {
                removeClient(client);
                return;
            }
        }
    }

}
