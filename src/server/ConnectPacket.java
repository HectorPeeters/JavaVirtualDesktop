package server;

public class ConnectPacket extends Packet {


    //Incoming
    public String i_username;
    public String i_password;

    public ConnectPacket(String[] data) {
        super(data);

        i_username = getData(1);
        i_password = getData(2);
    }

    //Outcoming
    public String o_username;
    public String o_password;

    public ConnectPacket(String username, String password) {
        super(PacketType.CONNECT);

        this.o_username = username;
        this.o_password = password;
    }

    @Override
    protected void indexOutgoingData() {
        addData(o_username);
        addData(o_password);
    }
}
