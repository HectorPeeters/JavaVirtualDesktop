package core.states;

import core.Client;
import core.assets.Loader;
import core.debug.Debug;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class StartupState extends BasicGameState {

    private boolean loadingComplete;

    public static Client client;

    public int getID() {
        return States.getID("Startup");
    }

    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        Loader.queueSpritesheet("/spritesheets/ui.xml");
    }

    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        Debug.draw(gameContainer, stateBasedGame, graphics);
    }

    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        if (!loadingComplete) {
            if (Loader.load()) {
                client = new Client("localhost", 4600);
                Debug.logInfo("");
                Debug.logInfo("Loading complete, loaded " + Loader.loadedImages + " images");
                Debug.logInfo("Press enter to continue");
                loadingComplete = true;
            }
        } else if (gameContainer.getInput().isKeyPressed(Input.KEY_RETURN)) {
            Debug.clearLog();
            stateBasedGame.enterState(States.getID("Login"));
        }
    }
}
