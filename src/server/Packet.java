package server;

import java.util.ArrayList;
import java.util.List;

public abstract class Packet {

    private List<String> dataList = new ArrayList<>();
    private PacketType packetType;

    public Packet(String[] data) {
        for (String segment : data) {
            dataList.add(segment);
        }
    }

    public Packet(PacketType packetType) {
        this.packetType = packetType;
    }

    protected String getData(int index) {
        return dataList.get(index);
    }

    protected void addData(String data) {
        addData(data, 0);
    }

    protected void addData(String data, int index) {
        dataList.add(index, data);
    }

    protected abstract void indexOutgoingData();

    public String getOutgoingData() {
        dataList.clear();
        indexOutgoingData();
        return compileOutgoingData();
    }

    protected String compileOutgoingData() {
        StringBuffer buffer = new StringBuffer(packetType.name()).append(";");
        for (int i = dataList.size() - 1; i >= 0; i--) {
            String data = dataList.get(i);
            buffer.append(data).append(";");
        }
        return buffer.toString();
    }

    @Override
    public String toString() {
        String name = "";
        for(String s : dataList)
            name += s + ";";
        return name;
    }
}
