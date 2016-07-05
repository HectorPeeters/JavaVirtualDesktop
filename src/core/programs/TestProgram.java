package core.programs;

import core.ui.UIHorizontalSlider;
import core.ui.UIRect;
import core.ui.uicomponents.UIText;
import core.ui.uicomponents.UITextField;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public class TestProgram extends AbstractProgram {

    private UIHorizontalSlider button;
    private UIText text;
    private UITextField textField;

    public TestProgram() {
        super("Test Program");
    }

    void initUI() {
        panel.setSize(500, 300);
        panel.setResizable(true);

        button = new UIHorizontalSlider(new UIRect(10, 30, 100, 20));
        panel.addComponent(button);

        text = new UIText("not updated", 10, 55);
        panel.addComponent(text);

        textField = new UITextField(new UIRect(10, 80, 100, 20));
        panel.addComponent(textField);
    }

    void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) {
        text.setText("slider value: " + button.getValue());
    }
}
