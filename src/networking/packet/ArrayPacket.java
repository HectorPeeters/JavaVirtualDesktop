package networking.packet;

import java.util.ArrayList;
import java.util.List;

public class ArrayPacket extends Packet {

    public ArrayPacket(String name) {
        super(name, new ArrayList<Packet>(), PacketType.ARRAY);
    }

    public Packet get(String name) {
        for (Packet packet : (ArrayList<Packet>) data)
            if (packet.name.equals(name))
                return packet;
        System.err.println("ArrayPacket - element " + name + " is not found");
        return null;
    }

    public List<Packet> getElements() {
        return (ArrayList<Packet>) data;
    }

    public void add(Packet packet) {
        if (packet instanceof ArrayPacket)
            return;
        ((ArrayList<Packet>) data).add(packet);
    }

    @Override
    public String getData() {
        String dataString = name + "|" + type;
        for (Packet p : (ArrayList<Packet>) data)
            dataString += "|" + p.getData();
        return dataString;
    }

    @Override
    public String toString() {
        String packetName = "ArrayPacket name='" + name + "\', size=" + getElements().size() + "{\n";
        for (Packet p : getElements()) {
            packetName += "\t" + p + "\n";
        }
        packetName += "}";
        return packetName;
    }
}
