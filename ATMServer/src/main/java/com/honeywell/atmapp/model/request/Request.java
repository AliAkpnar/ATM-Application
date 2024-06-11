package com.honeywell.atmapp.model.request;

import com.honeywell.atmapp.model.enums.Command;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class Request {
    private Command command;
    private String cardNumber;
    private String pin;
    private BigDecimal amount;
    private String newPin;
}
