package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;

public class ConnectedClient extends Thread {

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private boolean connected;

    public ConnectedClient(Socket socket) {
        this.socket = socket;
        try {
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        connected = true;
        System.out.println("Successfully connected to " + getRemoteSocketAddress());
    }

    public void run() {
        while (true) {
            Packet receive = receive();
            if (receive == null)
                break;
            send(ServerPacketResolver.resolvePacket(receive, this));
            ServerWindow.log("Received from client " + getRemoteSocketAddress() + "-> " + receive);
        }
        Server.INSTANCE.removeClient(this);
    }

    public void send(Packet packet) {
        try {
            out.writeUTF(packet.getOutgoingData());
            out.flush();
        } catch (IOException e) {
            connected = false;
            e.printStackTrace();
        }
    }

    public Packet receive() {
        try {
            String rawData = in.readUTF();
            String[] data = rawData.trim().split(";");
            PacketType type = PacketType.valueOf(data[0]);
            Packet packet = PacketDictionary.translatePacketType(type, data);
            return packet;
        } catch (IOException e) {
            connected = false;
        }
        return null;
    }

    public boolean isConnected() {
        return connected;
    }

    public SocketAddress getRemoteSocketAddress() {
        return socket.getRemoteSocketAddress();
    }
}