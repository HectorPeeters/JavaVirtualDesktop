package networking;

public class FloatPacket extends Packet {

    public FloatPacket(String name, float data) {
        super(name, data, PacketType.FLOAT);
    }

}
