package networking.client;

import networking.packet.Packet;
import networking.packet.PacketResolver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {

    private static boolean running;

    private static DataInputStream in;
    private static DataOutputStream out;

    public static void main(String[] args) throws Exception {
        running = true;
        String host = "localhost";
        int portNumber = 4600;
        System.out.println("Creating socket to '" + host + "' on port " + portNumber);

        Socket socket = new Socket(host, portNumber);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());

        String received;
        while (running) {
            if (!(received = in.readUTF()).equals("")) {
                Packet packet = PacketResolver.getPacket(received);
                System.out.println("received from server: ");
                System.out.println("\t" + packet + "\n");
                resolvePacket(packet);
            }
        }
        close();
        socket.close();
    }

    private static void resolvePacket(Packet packet) {
        if (packet.name.equals("disconnect")) {
            switch (packet.data.toString()) {
                case "kicked":
                    System.out.println("you got kicked");
                    break;

                case "shutdown":
                    System.out.println("server is shutting down");
                    break;
            }
            running = false;
        }
    }

    private static void send(Packet packet) {
        try {
            out.writeUTF(packet.getData());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void close() {
        try {
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
