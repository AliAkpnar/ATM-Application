package atm.client.model;

import atm.client.constant.Command;

import java.math.BigDecimal;

public class Request {
    private Command command;
    private String cardNumber;
    private String pin;
    private BigDecimal amount;
    private String newPin;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getNewPin() {
        return newPin;
    }

    public void setNewPin(String newPin) {
        this.newPin = newPin;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }

    @Override
    public String toString() {
        return "Request{" +
                "command='" + command + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", pin='" + pin + '\'' +
                ", amount=" + amount +
                ", newPin='" + newPin + '\'' +
                '}';
    }

}
