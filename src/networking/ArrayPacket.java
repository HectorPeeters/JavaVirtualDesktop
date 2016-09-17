package networking;

import java.util.ArrayList;

public class ArrayPacket extends Packet {

    public ArrayPacket(String name) {
        super(name, new ArrayList<Packet>(), PacketType.ARRAY);
    }

    public Packet get(String name) {
        for (Packet packet : (ArrayList<Packet>) data)
            if (packet.name.equals(name))
                return packet;
        return null;
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
}
