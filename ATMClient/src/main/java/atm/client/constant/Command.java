package atm.client.constant;

public enum Command {
    LOGIN,
    VIEW_BALANCE,
    WITHDRAW_CASH,
    DEPOSIT_CASH,
    CHANGE_PIN,
    LOGOFF,
    UNKNOWN;

    public static Command getValueFrom(String command) {
        for (Command value : Command.values()) {
            if(value.name().equalsIgnoreCase(command)) {
                return value;
            }
        }
        return Command.UNKNOWN;
    }
}
