package core.debug;

public class LogMessage {

    private String message;
    private LogColor color;

    public LogMessage(String message, LogColor color) {
        this.message = message;
        this.color = color;
    }

    public String getMessage() {
        return message;
    }

    public LogColor getColor() {
        return color;
    }

}
