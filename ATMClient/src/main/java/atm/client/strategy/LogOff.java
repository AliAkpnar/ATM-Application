package atm.client.strategy;

import atm.client.constant.Command;
import atm.client.model.Request;

public class LogOff implements Strategy<String[]> {
    private final Command type = Command.LOGOFF;
    @Override
    public Request execute(String[] args) {
        Request request = new Request();
        request.setCommand(type);
        return request;
    }

    @Override
    public Command getType() {
        return type;
    }

    public static Strategy builder() {
        return new LogOff();
    }
}
