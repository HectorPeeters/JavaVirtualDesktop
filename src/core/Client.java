package core;

import packet.Packet;
import packet.PacketResolver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client extends Thread {

    private boolean running;

    private DataInputStream in;
    private DataOutputStream out;

    private String host;
    private int port;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
        start();
    }

    public void run() {
        try {
            running = true;
            System.out.println("Creating socket to '" + host + "' on port " + port);

            Socket socket = new Socket(host, port);

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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void resolvePacket(Packet packet) {
        if (packet.name.equals("disconnect")) {
            switch (packet.data.toString()) {
                case "kicked":
                    break;
                case "shutdown":
                    break;
            }
            running = false;
        }
    }

    public void send(Packet packet) {
        try {
            out.writeUTF(packet.getOutput());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void close() {
        try {
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
