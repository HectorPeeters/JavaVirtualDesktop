package networking.server;

import networking.packet.StringPacket;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerWindow extends JFrame {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss");

    public static final String TITLE = "Server";
    private static ServerWindow INSTANCE;

    private JTextArea console;
    private JList<ServerClient> listUsers;

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
        setAlwaysOnTop(true);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                Server.stopServer();
            }
        });
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

        listUsers.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e) && !listUsers.isSelectionEmpty() && listUsers.locationToIndex(e.getPoint()) == listUsers.getSelectedIndex()) {
                    JPopupMenu menu = new JPopupMenu();
                    JMenuItem kick = new JMenuItem("disconnect");

                    kick.addActionListener(e1 -> {
                        ServerClient client = listUsers.getSelectedValue();
                        client.send(new StringPacket("disconnect", "kicked"));
                    });

                    menu.add(kick);
                    menu.show(e.getComponent(), e.getX(), e.getY());
                    menu.show(listUsers, e.getX(), e.getY());
                }
            }
        });
    }

    public void updateView() {
        DefaultListModel<ServerClient> model = new DefaultListModel<>();
        Server.clients.forEach(model::addElement);
        listUsers.setModel(model);
    }

    public static void log(String message) {
        INSTANCE.console.append(DATE_FORMAT.format(new Date()) + " " + message + "\n");
    }

    public static ServerWindow getInstance() {
        return INSTANCE;
    }

}
