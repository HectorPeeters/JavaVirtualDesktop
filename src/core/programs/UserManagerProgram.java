package core.programs;

import core.database.DatabaseManager;
import core.debug.Debug;
import core.ui.UIRect;
import core.ui.uicomponents.UIButton;
import core.ui.uicomponents.UIPasswordField;
import core.ui.uicomponents.UITextField;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public class UserManagerProgram extends AbstractProgram {

    private UITextField usernameField;
    private UIPasswordField passwordField;
    private UIButton button;

    public UserManagerProgram() {
        super("User Manager");
    }

    void initUI() {
        panel.setResizable(false);
        panel.setSize(500, 300);

        usernameField = new UITextField(new UIRect(10, 30, 200, 20));
        panel.addComponent(usernameField);

        passwordField = new UIPasswordField(new UIRect(290, 30, 200, 20));
        //panel.addComponent(passwordField);

        button = new UIButton(new UIRect(150, 55, 200, 20));
        panel.addComponent(button);
    }

    void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) {
        if (button.isPressed()) {
            Debug.logInfo("User " + usernameField.getText() + " exists: " + DatabaseManager.doesUserExist(usernameField.getText()));
        }
    }
}
