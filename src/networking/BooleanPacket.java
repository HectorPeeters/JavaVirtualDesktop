package networking;

public class BooleanPacket extends Packet {

    public BooleanPacket(String name, Object data) {
        super(name, data, PacketType.BOOLEAN);
    }

}
