package networking.packet;

public class IntPacket extends Packet {

    public IntPacket(String name, int data) {
        super(name, data, PacketType.INT);
    }

}
