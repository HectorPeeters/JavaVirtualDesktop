package server;

public class AcceptPacket extends Packet {


    //Incoming
    public String i_accept = "false";

    public AcceptPacket(String[] data) {
        super(data);

        i_accept = getData(1);
    }

    //Outgoing
    public String o_id;
    public String o_accept;

    public AcceptPacket(boolean accepted) {
        super(PacketType.ACCEPT);
        if (accepted)
            o_accept = "true";
        else
            o_accept = "false";
    }

    @Override
    protected void indexOutgoingData() {
        addData(o_accept);
    }

    public boolean isAccepted() {
        return i_accept.equals("true");
    }

}
