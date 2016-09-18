package networking.server;

import networking.packet.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;

public class ServerClient {

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
        this.connected = true;
    }

    public void send(Packet packet) {
        try {
            out.writeUTF(packet.getData());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SocketAddress getRemoteSocketAddress() {
        return socket.getRemoteSocketAddress();
    }

    public void close() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        connected = false;
    }

    @Override
    public String toString() {
        return getRemoteSocketAddress() + "";
    }
}
