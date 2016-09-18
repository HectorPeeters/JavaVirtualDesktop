package networking.packet;

public class PacketResolver {

    public static Packet getPacket(String data) {
        Packet p = null;
        String[] split = data.split("\\|");
        switch (PacketType.valueOf(split[1])) {
            case INT:
                p = new IntPacket(split[0], Integer.parseInt(split[2]));
                break;
            case FLOAT:
                p = new FloatPacket(split[0], Float.parseFloat(split[2]));
                break;
            case BOOLEAN:
                p = new BooleanPacket(split[0], Boolean.parseBoolean(split[2]));
                break;
            case STRING:
                p = new StringPacket(split[0], split[2]);
                break;
            case ARRAY:
                p = new ArrayPacket(split[0]);
                for (int i = 2; i < split.length; i += 3) {
                    Packet packetToAdd = getPacket(split[i] + "|" + split[i + 1] + "|" + split[i + 2]);
                    ((ArrayPacket) p).add(packetToAdd);
                }
                break;
        }
        p.name = p.name.replaceAll("\u0012", "").replaceAll("\u0017", "").replaceAll("\u0019", "").replaceAll((char)0 + "", "").replaceAll("\u001B", "");
        return p;
    }

}
