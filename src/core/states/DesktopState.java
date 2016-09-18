package core.states;

import core.Settings;
import core.assets.Loader;
import core.debug.Debug;
import core.programs.ProgramManager;
import core.programs.RepoInstallerProgram;
import core.programs.TestProgram;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class DesktopState extends BasicGameState {

    private ProgramManager programManager;

    private boolean loadedVariables;

    private Image wallpaper;

    public int getID() {
        return States.getID("Desktop");
    }

    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

    }

    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        wallpaper.draw(0, 0, Settings.WIDTH, Settings.HEIGHT);
        programManager.draw(gameContainer, stateBasedGame, graphics);
        Debug.draw(gameContainer, stateBasedGame, graphics);
    }

    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        if (!loadedVariables)
            loadVariables(gameContainer, stateBasedGame);
        programManager.update(gameContainer, stateBasedGame, i);
        if (gameContainer.getInput().isKeyDown(Input.KEY_D)) {
            System.out.println(Runtime.getRuntime().totalMemory() / (1024 * 1024) + "MB");

        }
        if (gameContainer.getInput().isKeyPressed(Input.KEY_SPACE)) {
            programManager.addProgram(new TestProgram());
        }
    }

    private void loadVariables(GameContainer gameContainer, StateBasedGame stateBasedGame) {
        programManager = new ProgramManager();

        //programManager.addProgram(new UserManagerProgram());
        programManager.addProgram(new RepoInstallerProgram());

        wallpaper = Loader.getImage("/wallpaper.png");
        Debug.logInfo("Successfully initialized all variables");
        loadedVariables = true;
    }
}
