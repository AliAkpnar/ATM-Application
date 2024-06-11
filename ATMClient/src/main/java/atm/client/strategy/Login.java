package atm.client.strategy;

import atm.client.constant.Command;
import atm.client.model.Request;

public class Login implements Strategy<String[]> {
    private final Command type = Command.LOGIN;

    @Override
    public Request execute(String[] args) {
        Request request = new Request();
        request.setCommand(type);
        request.setCardNumber(args[2]);
        request.setPin(args[3]);
        return request;
    }

    @Override
    public Command getType() {
        return type;
    }

    public static Strategy builder() {
        return new Login();
    }

}
