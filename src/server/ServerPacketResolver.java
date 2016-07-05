package server;

import core.database.DatabaseManager;
import core.database.Hasher;

public final class ServerPacketResolver {

    public static Packet resolvePacket(Packet packet, ConnectedClient client) {
        if (packet instanceof ConnectPacket) {
            ConnectPacket connect = ((ConnectPacket) packet);
            boolean accept = false;
            if (DatabaseManager.doesUserExist(connect.i_username)) {
                String password = DatabaseManager.getPassword(connect.i_username);
                if (password.equals(""))
                    accept = false;
                if (password.equals(Hasher.hash(connect.i_password)))
                    accept = true;
            }
            return new AcceptPacket(accept);
        }
        return null;
    }

}
