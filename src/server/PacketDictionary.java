package server;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public final class PacketDictionary {

    private static final Map<PacketType, Class<? extends Packet>> PACKET_DICTIONARY = new HashMap<>();

    static {
        PACKET_DICTIONARY.put(PacketType.CONNECT, ConnectPacket.class);
        PACKET_DICTIONARY.put(PacketType.ACCEPT, AcceptPacket.class);
    }

    public static Packet translatePacketType(PacketType type, String[] data) {
        Class<? extends Packet> clazz = PACKET_DICTIONARY.get(type);
        if (clazz != null) {
            try {
                return clazz.getConstructor(String[].class).newInstance((Object) data);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        System.err.println("packet type " + type + " is not recognized");
        return null;
    }

}
