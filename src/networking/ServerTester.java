package networking;

public class ServerTester {

    public static void main(String[] args) {
        ArrayPacket array = new ArrayPacket("login");
        array.add(new StringPacket("username", "hector"));
        array.add(new StringPacket("password", "p@ssword"));

        String packetData = array.getData();

        ArrayPacket p = (ArrayPacket) PacketResolver.getPacket(packetData);
        System.out.println(p.get("username").data);
        System.out.println(p.get("password").data);
    }

}
