package networking.packet;

public class BooleanPacket extends Packet {

    public BooleanPacket(String name, boolean data) {
        super(name, data, PacketType.BOOLEAN);
    }

}
