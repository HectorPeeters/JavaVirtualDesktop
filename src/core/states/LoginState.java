package core.states;

import core.InputKeyListener;
import core.Settings;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import server.AcceptPacket;
import server.Client;
import server.ConnectPacket;
import server.Packet;

public class LoginState extends BasicGameState {

    public static Client client;

    private static String loginString = "Login:";
    private static String passwordString = "Password:";
    private String currentLogin = "";
    private String currentPassword = "";
    private boolean loginPhase = true;

    private InputKeyListener keyListener;

    public int getID() {
        return States.getID("Login");
    }

    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        client = new Client();
    }

    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        if (loginPhase) {
            graphics.drawString(loginString, (Settings.WIDTH - gameContainer.getDefaultFont().getWidth(loginString)) / 2, (Settings.HEIGHT - gameContainer.getDefaultFont().getHeight(loginString)) / 2 - 50);
            graphics.drawString(keyListener.getString(), (Settings.WIDTH - gameContainer.getDefaultFont().getWidth(keyListener.getString())) / 2, (Settings.HEIGHT - gameContainer.getDefaultFont().getHeight(keyListener.getString())) / 2);
        } else {
            String password = getPasswordString(keyListener.getString());
            graphics.drawString(passwordString, (Settings.WIDTH - gameContainer.getDefaultFont().getWidth(passwordString)) / 2, (Settings.HEIGHT - gameContainer.getDefaultFont().getHeight(passwordString)) / 2 - 50);
            graphics.drawString(password, (Settings.WIDTH - gameContainer.getDefaultFont().getWidth(password)) / 2, (Settings.HEIGHT - gameContainer.getDefaultFont().getHeight(password)) / 2);
        }
    }

    private String getPasswordString(String password) {
        String result = "";
        for(int i = 0; i < password.length(); i ++) {
            result += "*";
        }
        return result;
    }

    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        if(keyListener == null) {
            keyListener = new InputKeyListener();
            keyListener.setEnabled(true);
            gameContainer.getInput().addKeyListener(keyListener);
        }
        if(gameContainer.getInput().isKeyPressed(Input.KEY_RETURN) && !keyListener.getString().equals("")) {
            if(loginPhase) {
                loginPhase = false;
                currentLogin = keyListener.getString();
                keyListener.setString("");
            } else {
                currentPassword = keyListener.getString();
                keyListener.setString("");
                if(!login(currentLogin, currentPassword)) {
                    loginPhase = true;
                } else {
                    stateBasedGame.enterState(States.getID("Desktop"));
                }
            }
        }
    }

    private boolean login(String username, String password) {
        client.sendPacket(new ConnectPacket(username, password));
        Packet response = client.waitAndReceive();
        AcceptPacket acceptPacket = (AcceptPacket) response;
        return acceptPacket.isAccepted();
    }
}
