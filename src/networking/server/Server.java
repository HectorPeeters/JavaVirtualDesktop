package networking.server;

import networking.packet.StringPacket;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Server {

    public static List<ServerClient> clients = new ArrayList<>();

    private static ServerSocket socket;
    private static boolean running;

    public static void main(String[] args) throws Exception {
        running = true;
        new ServerWindow();
        socket = new ServerSocket(4600);
        ServerWindow.log("Successfully started server on port " + socket.getLocalPort());

        while (running) {
            ServerClient client = new ServerClient(socket.accept());
            addClient(client);

            ServerWindow.log("Connected to " + client.getRemoteSocketAddress());
        }
    }

    public static void stopServer() {
        Iterator i = clients.iterator();
        while (i.hasNext()) {
            ServerClient c = (ServerClient) i.next();
            c.send(new StringPacket("disconnect", "shutdown"));
            i.remove();
        }
        running = false;
    }

    public static void addClient(ServerClient client) {
        clients.add(client);
        ServerWindow.getInstance().updateView();
    }

    public static void forceRemoveClient(ServerClient client) {
        clients.remove(client);
        client.close();
        ServerWindow.getInstance().updateView();
    }

}
