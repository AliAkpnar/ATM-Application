package atm.client.strategy;

import atm.client.constant.Command;
import atm.client.model.Request;

public interface Strategy<T> {

    Request execute(T args) throws Exception;

    Command getType();
}
