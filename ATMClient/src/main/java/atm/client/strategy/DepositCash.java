package atm.client.strategy;

import atm.client.constant.Command;
import atm.client.model.Request;

import java.math.BigDecimal;

public class DepositCash implements Strategy<String[]> {
    private final Command type = Command.DEPOSIT_CASH;

    @Override
    public Request execute(String[] args) {
        Request request = new Request();
        request.setCommand(type);
        request.setCardNumber(args[2]);
        request.setAmount(new BigDecimal(args[3]));
        return request;
    }

    @Override
    public Command getType() {
        return type;
    }

    public static Strategy builder() {
        return new DepositCash();
    }

}
