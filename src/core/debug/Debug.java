package core.debug;

import core.Settings;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;
import java.util.List;

public final class Debug {

    public static final long startTime = System.currentTimeMillis();

    private static List<LogMessage> messages = new ArrayList<>();
    private static int yScroll;
    private static int lineSpacing = 18;

    public static void logInfo(String message) {
        addMessage("[" + getRunningTime() + "] " + message, LogColor.GREEN);
    }

    public static void logError(String message) {
        addMessage("[" + getRunningTime() + "] " + message, LogColor.RED);
    }

    public static float getRunningTime() {
        return (System.currentTimeMillis() - startTime) / 1000f;
    }

    public static void addMessage(String message, LogColor color) {
        messages.add(new LogMessage(message, color));
        int yPos = (yScroll + messages.size() - 1) * lineSpacing;
        if (yPos >= Settings.HEIGHT - lineSpacing) {
            yScroll += lineSpacing;
        }
    }

    public static void draw(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
        for (int i = 0; i < messages.size(); i++) {
            LogMessage message = messages.get(i);
            graphics.setColor(message.getColor().getColor());
            graphics.drawString(message.getMessage(), 0, i * lineSpacing - yScroll);
        }
    }

    public static void clearLog() {
        messages.clear();
        yScroll = 0;
    }

}
