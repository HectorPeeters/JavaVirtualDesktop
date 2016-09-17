package networking;

public class PacketResolver {

    public static Packet getPacket(String data) {
        String[] split = data.split("\\|");
        switch (PacketType.valueOf(split[1])) {
            case INT:
                return new IntPacket(split[0], Integer.parseInt(split[2]));
            case FLOAT:
                return new FloatPacket(split[0], Float.parseFloat(split[2]));
            case BOOLEAN:
                return new BooleanPacket(split[0], Boolean.parseBoolean(split[2]));
            case STRING:
                return new StringPacket(split[0], split[2]);
            case ARRAY:
                Packet packet = new ArrayPacket(split[0]);
                for (int i = 2; i < split.length; i += 3) {
                    Packet packetToAdd = getPacket(split[i] + "|" + split[i + 1] + "|" + split[i + 2]);
                    ((ArrayPacket) packet).add(packetToAdd);
                }
                return packet;
        }
        return null;
    }

}
