package server;


import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerWindow extends JFrame {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss");

    public static final String TITLE = "Server";
    private static ServerWindow INSTANCE;

    private JTextArea console;
    private JList<SocketAddress> listUsers;

    public ServerWindow() {
        createView();
        INSTANCE = this;

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        setTitle(TITLE);
        setSize(600, 400);
        setResizable(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createView() {
        JPanel panel = new JPanel();
        getContentPane().add(panel);

        panel.setLayout(new BorderLayout());

        console = new JTextArea();
        console.setEditable(false);
        ((DefaultCaret) console.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        JScrollPane consoleSP = new JScrollPane(console);
        consoleSP.setBorder(BorderFactory.createTitledBorder("Console Output"));
        panel.add(consoleSP, BorderLayout.CENTER);

        listUsers = new JList<>();
        JScrollPane listUsersSP = new JScrollPane(listUsers);
        listUsersSP.setBorder(BorderFactory.createTitledBorder("Connected Users"));
        listUsersSP.setPreferredSize(new Dimension(200, 0));
        panel.add(listUsersSP, BorderLayout.EAST);
    }

    public void updateView() {
        DefaultListModel<SocketAddress> model = new DefaultListModel<>();
        for(ConnectedClient client : Server.INSTANCE.connectedClients) {
            model.addElement(client.getRemoteSocketAddress());
        }
        listUsers.setModel(model);
    }

    public static void log(String message) {
        INSTANCE.console.append(DATE_FORMAT.format(new Date()) + " " + message + "\n");
    }

    public static ServerWindow getInstance() {
        return INSTANCE;
    }
}
