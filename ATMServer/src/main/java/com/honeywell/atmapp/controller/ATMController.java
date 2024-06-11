package com.honeywell.atmapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.honeywell.atmapp.persistence.entity.ATMCard;
import com.honeywell.atmapp.model.request.Request;
import com.honeywell.atmapp.service.ATMService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequiredArgsConstructor
public class ATMController {

    private final ATMService atmService;
    private final ObjectMapper objectMapper;
    private final static String PERMISSION_DENIED_TEXT = "You dont have enough permission!!!";
    private static final Map<String, Request> authenticatedUsers = new ConcurrentHashMap<>();

    @MessageMapping("/process")
    @SendTo("/")
    public String handle(String request, SimpMessageHeaderAccessor accessor) throws JsonProcessingException {
        return process(objectMapper.readValue(request, Request.class), accessor);
    }

    public String process(Request request, SimpMessageHeaderAccessor accessor) {
        switch (request.getCommand()) {
            case LOGIN -> {
                ATMCard card = atmService.login(request.getCardNumber(), request.getPin());
                if (card != null) {
                    authenticatedUsers.put(accessor.getSessionId(), request);
                    return "Logged in successfully.";
                } else {
                    return "Invalid card number or PIN.";
                }
            }
            case VIEW_BALANCE -> {
                if (validateAuthentication(accessor.getSessionId(), request.getCardNumber())) {
                    BigDecimal balance = atmService.viewBalance(request.getCardNumber());
                    return "Available balance: " + balance.toPlainString();
                } else {
                    return PERMISSION_DENIED_TEXT;
                }
            }
            case WITHDRAW_CASH -> {
                if (validateAuthentication(accessor.getSessionId(), request.getCardNumber())) {
                    BigDecimal amountToWithdraw = request.getAmount();
                    BigDecimal withdrawnAmount = atmService.withdrawCash(request.getCardNumber(), amountToWithdraw);
                    if (BigDecimal.ZERO.compareTo(withdrawnAmount) < 0) {
                        return "Withdrawn amount: " + withdrawnAmount.toPlainString();
                    } else {
                        return "Insufficient balance or invalid card.";
                    }
                } else {
                    return PERMISSION_DENIED_TEXT;
                }

            }
            case DEPOSIT_CASH -> {
                if (validateAuthentication(accessor.getSessionId(), request.getCardNumber())) {
                    BigDecimal amountToDeposit = request.getAmount();
                    BigDecimal depositedAmount = atmService.depositCash(request.getCardNumber(), amountToDeposit);
                    return "Deposited amount: " + depositedAmount.toPlainString();
                } else {
                    return PERMISSION_DENIED_TEXT;
                }
            }
            case CHANGE_PIN -> {
                if (validateAuthentication(accessor.getSessionId(), request.getCardNumber())) {
                    String newPin = request.getNewPin();
                    boolean pinChanged = atmService.changePin(request.getCardNumber(), newPin);
                    if (pinChanged) {
                        return "PIN changed successfully.";
                    } else {
                        return "Invalid card.";
                    }
                } else {
                    return PERMISSION_DENIED_TEXT;
                }

            }
            case LOGOFF -> {
                authenticatedUsers.remove(accessor.getSessionId());
                return "Goodbye";
            }
            default -> {
                return "Invalid command.";
            }
        }
    }

    private static boolean validateAuthentication(String sessionId, String cardNo) {
        return authenticatedUsers.get(sessionId) != null
                && authenticatedUsers.get(sessionId).getCardNumber().equals(cardNo);
    }
}