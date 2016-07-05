package core.programs;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProgramManager {

    private List<AbstractProgram> programs = new ArrayList<>();

    public void draw(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
        for (int i = programs.size() - 1; i >= 0; --i)
            programs.get(i).renderProgram(gameContainer, stateBasedGame, graphics);
    }

    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) {
        Iterator iterator = programs.iterator();
        while (iterator.hasNext()) {
            AbstractProgram program = (AbstractProgram) iterator.next();
            if (program.isTerminated())
                iterator.remove();
        }
        for (int j = 0; j < programs.size(); j++) {
            AbstractProgram program = programs.get(j);
            if (program.updateProgram(gameContainer, stateBasedGame, i)) {
                programs.remove(program);
                programs.add(0, program);
                break;
            }
        }
    }

    public void addProgram(AbstractProgram program) {
        programs.add(program);
    }

}
