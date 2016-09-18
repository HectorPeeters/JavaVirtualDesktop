package networking.packet;

public class StringPacket extends Packet {

    public StringPacket(String name, String data) {
        super(name, data, PacketType.STRING);
    }

}
