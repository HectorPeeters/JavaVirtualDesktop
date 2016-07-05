package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    private Socket socket;

    private DataOutputStream out;
    private DataInputStream in;

    public Client() {
        try {
            socket = new Socket("localhost", 4444);
            socket.setSoTimeout(1000000);
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Packet waitAndReceive() {
        Packet packet;
        while ((packet = receive()) == null) {

        }
        return packet;
    }

    public Packet receive() {
        try {
            String rawData = in.readUTF();
            String[] data = rawData.trim().split(";");
            PacketType type = PacketType.valueOf(data[0]);
            Packet packet = PacketDictionary.translatePacketType(type, data);
            return packet;
        } catch (IOException e) {

        }
        return null;
    }

    public void sendPacket(Packet packet) {
        try {
            out.writeUTF(packet.getOutgoingData());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Client();
    }

}
