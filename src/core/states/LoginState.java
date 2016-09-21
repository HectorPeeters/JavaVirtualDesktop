package core.states;

import core.InputKeyListener;
import core.Settings;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import packet.*;

public class LoginState extends BasicGameState {

    private static final String loginString = "Login:";
    private static final String passwordString = "Password:";
    private String currentLogin = "";
    private boolean loginPhase = true;
    private boolean checking = false;
    private boolean loggedIn = false;

    private final PacketListener loginPacketListener = new PacketListener("login") {
        @Override
        public void packetReceived(Packet p) {
            if (checking) {
                if (p.data.toString().equals("true")) {
                    loggedIn = true;
                } else {
                    checking = false;
                    loginPhase = true;
                    keyListener.setString("");
                    keyListener.setEnabled(true);
                }
            }
        }
    };

    private InputKeyListener keyListener;

    public int getID() {
        return States.getID("Login");
    }

    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        PacketResolver.addPacketListener(loginPacketListener);
    }

    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        if (!checking) {
            if (loginPhase) {
                graphics.drawString(loginString, (Settings.WIDTH - gameContainer.getDefaultFont().getWidth(loginString)) / 2, (Settings.HEIGHT - gameContainer.getDefaultFont().getHeight(loginString)) / 2 - 50);
                graphics.drawString(keyListener.getString(), (Settings.WIDTH - gameContainer.getDefaultFont().getWidth(keyListener.getString())) / 2, (Settings.HEIGHT - gameContainer.getDefaultFont().getHeight(keyListener.getString())) / 2);
            } else {
                String password = getPasswordString(keyListener.getString());
                graphics.drawString(passwordString, (Settings.WIDTH - gameContainer.getDefaultFont().getWidth(passwordString)) / 2, (Settings.HEIGHT - gameContainer.getDefaultFont().getHeight(passwordString)) / 2 - 50);
                graphics.drawString(password, (Settings.WIDTH - gameContainer.getDefaultFont().getWidth(password)) / 2, (Settings.HEIGHT - gameContainer.getDefaultFont().getHeight(password)) / 2);
            }
        } else {
            graphics.drawString("Logging in...", (Settings.WIDTH - gameContainer.getDefaultFont().getWidth("Logging in...")) / 2, (Settings.HEIGHT - gameContainer.getDefaultFont().getHeight("Logging in...")) / 2);
        }
    }

    private String getPasswordString(String password) {
        String result = "";
        for (int i = 0; i < password.length(); i++) {
            result += "*";
        }
        return result;
    }

    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        if (loggedIn) {
            PacketResolver.removePacketListener(loginPacketListener);
            stateBasedGame.enterState(States.getID("Desktop"));
        }
        if (keyListener == null) {
            keyListener = new InputKeyListener();
            keyListener.setEnabled(true);
            gameContainer.getInput().addKeyListener(keyListener);
        }
        if (gameContainer.getInput().isKeyPressed(Input.KEY_RETURN) && !keyListener.getString().equals("") && !checking) {
            if (loginPhase) {
                loginPhase = false;
                currentLogin = keyListener.getString();
                keyListener.setString("");
            } else {
                sendLogin(currentLogin, keyListener.getString());
                keyListener.setString("");
                checking = true;
                keyListener.setEnabled(false);
            }
        }
    }

    private void sendLogin(String username, String password) {
        ArrayPacket packet = new ArrayPacket("login");
        packet.add(new StringPacket("username", username));
        packet.add(new StringPacket("password", password));
        StartupState.client.send(packet);
    }
}
