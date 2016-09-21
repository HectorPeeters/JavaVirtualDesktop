package server;

import packet.StringPacket;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Server {

    public static List<ServerClient> clients = new ArrayList<>();

    private static boolean running;

    private static Thread disconnectThread;

    public static void main(String[] args) throws Exception {
        String port = JOptionPane.showInputDialog("Please input server port:");

        running = true;
        new ServerWindow();
        ServerSocket socket = new ServerSocket(Integer.parseInt(port));
        ServerWindow.log("Successfully started server on port " + socket.getLocalPort());

        disconnectThread = new Thread(() -> {
            while (running) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                clients.stream().filter(client -> !client.isReachable()).forEach(Server::forceRemoveClient);
            }
        });
        disconnectThread.start();

        while (running) {
            ServerClient client = null;
            try {
                client = new ServerClient(socket.accept());
            } catch (IOException e) {
                e.printStackTrace();
            }
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
