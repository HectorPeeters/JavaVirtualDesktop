package networking.server;

import networking.packet.Packet;
import networking.packet.PacketResolver;

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
            out.writeUTF(packet.getData());
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
                Packet packet = PacketResolver.getPacket(received);
                ServerWindow.log("received from " + getRemoteSocketAddress() + ": ");
                ServerWindow.log("\t" + packet + "\n");
                resolvePacket(packet);
            }
        }
    }

    private static void resolvePacket(Packet packet) {

    }

    public SocketAddress getRemoteSocketAddress() {
        return socket.getRemoteSocketAddress();
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

    @Override
    public String toString() {
        return getRemoteSocketAddress() + "";
    }
}
