package networking;

public class StringPacket extends Packet {

    public StringPacket(String name, Object data) {
        super(name, data, PacketType.STRING);
    }

}
