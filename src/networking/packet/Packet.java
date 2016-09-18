package networking.packet;

public abstract class Packet {

    public String name;
    public PacketType type;
    public Object data;

    public Packet(String name, Object data, PacketType type) {
        this.name = name;
        this.data = data;
        this.type = type;
    }

    public String getData() {
        return name + "|" + type + "|" + data;
    }

    @Override
    public String toString() {
        return "Packet {" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", data=" + data +
                '}';
    }
}
