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
        while (running) {
            ServerClient client = new ServerClient(socket.accept());
            clients.add(client);
            client.send(new StringPacket("login", "test"));

            ServerWindow.log("Connected to " + client.getRemoteSocketAddress());
            ServerWindow.getInstance().updateView();
        }
    }

    public static void kick(ServerClient client) {
        clients.remove(client);
        client.send(new StringPacket("disconnect", "kicked"));
        client.close();
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

}
