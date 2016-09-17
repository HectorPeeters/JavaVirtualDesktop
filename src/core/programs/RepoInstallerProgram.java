package core.programs;

import core.ui.UIRect;
import core.ui.uicomponents.UIButton;
import core.ui.uicomponents.UITextField;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public class RepoInstallerProgram extends AbstractProgram {

    private UITextField usernameField;
    private UIButton button;

    public RepoInstallerProgram() {
        super("Repo Installer");
    }

    void initUI() {
        panel.setResizable(false);
        panel.setSize(130, 80);

        usernameField = new UITextField(new UIRect(10, 30, 80, 20));
        panel.addComponent(usernameField);

        button = new UIButton(new UIRect(10, 55, 80, 20));
        panel.addComponent(button);
    }

    void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) {
        if (button.isPressed()) {

        }
    }

}
