package atm.client.strategy;

import atm.client.constant.Command;
import atm.client.model.Request;

public class AvailableCredit implements Strategy<String[]> {
    private final Command type = Command.VIEW_BALANCE;
    @Override
    public Request execute(String[] args) {
        Request request = new Request();
        request.setCommand(type);
        request.setCardNumber(args[2]);
        return request;
    }

    @Override
    public Command getType() {
        return type;
    }

    public static Strategy builder() {
        return new AvailableCredit();
    }
}
