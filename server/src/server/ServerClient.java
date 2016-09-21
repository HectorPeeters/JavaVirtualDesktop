package server;

import packet.ArrayPacket;
import packet.BooleanPacket;
import packet.Packet;
import packet.PacketResolver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;

public class ServerClient extends Thread {

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private boolean connected;

    public ServerClient(Socket socket) {
        this.socket = socket;
        try {
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        connected = true;
        start();
    }

    public void send(Packet packet) {
        try {
            out.writeUTF(packet.getOutput());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String received;
        while (connected) {
            try {
                received = in.readUTF();
            } catch (IOException e) {
                Server.forceRemoveClient(this);
                return;
            }
            if (!received.equals("")) {
                ServerWindow.log("received from " + getRemoteSocketAddress() + ": " + received);
                Packet packet = PacketResolver.getPacket(received);
                ServerWindow.log("received from " + getRemoteSocketAddress() + ": ");
                ServerWindow.log("\t" + packet + "\n");
                resolvePacket(packet);
            }
        }
    }

    private void resolvePacket(Packet packet) {
        switch (packet.name) {
            case "login":
                ArrayPacket array = (ArrayPacket) packet;
                String username = array.get("username").data.toString();
                String password = array.get("password").data.toString();
                if (!Users.doesUserExist(username)) {
                    System.out.println("user not found");
                    send(new BooleanPacket("login", false));
                } else {
                    send(new BooleanPacket("login", Users.getPassword(username).equals(password)));
                }
        }
    }

    public void close() {
        connected = false;
        SocketAddress address = socket.getRemoteSocketAddress();
        try {
            if (in != null)
                in.close();
            if (out != null)
                out.close();
            if (socket != null)
                socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ServerWindow.log("Disconnected " + address);
    }

    public SocketAddress getRemoteSocketAddress() {
        return socket.getRemoteSocketAddress();
    }

    @Override
    public String toString() {
        return getRemoteSocketAddress() + "";
    }

    public boolean isReachable() {
        try {
            return socket.getInetAddress().isReachable(20);
        } catch (IOException e) {
            return false;
        }
    }

}