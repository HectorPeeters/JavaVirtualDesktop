package core;

import core.debug.Debug;
import core.states.DesktopState;
import core.states.LoginState;
import core.states.StartupState;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Main extends StateBasedGame {

    public Main() {
        super("PC");
    }

    public void initStatesList(GameContainer gameContainer) throws SlickException {
        addState(new StartupState());
        addState(new LoginState());
        addState(new DesktopState());
        Debug.logInfo("Successfully initialized all states");
    }

    public static void main(String[] args) {
        try {
            System.setProperty("java.library.path", "./natives/linux");
            Debug.logInfo("Started program");
            AppGameContainer container = new AppGameContainer(new Main());
            container.setDisplayMode(Settings.WIDTH, Settings.HEIGHT, false);
            container.setTargetFrameRate(120);
            container.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}