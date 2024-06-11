package com.honeywell.atmapp.service;

import com.honeywell.atmapp.persistence.entity.ATMCard;
import com.honeywell.atmapp.persistence.repository.ATMRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ATMService {

    private final ATMRepository atmRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        if (atmRepository.findById(1L).isEmpty()) {
            ATMCard atmCard1 = new ATMCard();
            atmCard1.setId(1L);
            atmCard1.setCardNumber("1234567890");
            atmCard1.setPin(passwordEncoder.encode("1234"));
            atmCard1.setAvailableBalance(new BigDecimal(1000));
            atmRepository.save(atmCard1);
        }
        if (atmRepository.findById(2L).isEmpty()) {
            ATMCard atmCard2 = new ATMCard();
            atmCard2.setId(2L);
            atmCard2.setCardNumber("0987654321");
            atmCard2.setPin(passwordEncoder.encode("4321"));
            atmCard2.setAvailableBalance(new BigDecimal(2000));
            atmRepository.save(atmCard2);
        }
    }

    public ATMCard login(String cardNumber, String pin) {
        ATMCard atmCard = atmRepository.findByCardNumber(cardNumber).orElse(null);
        if (atmCard != null && passwordEncoder.matches(pin, atmCard.getPin())) {
            return atmCard;
        }
        return null;
    }

    public BigDecimal viewBalance(String cardNumber) {
        ATMCard atmCard = atmRepository.findByCardNumber(cardNumber).orElse(null);
        if (atmCard != null) {
            return atmCard.getAvailableBalance();
        }
        return BigDecimal.ZERO;
    }

    @Transactional
    public BigDecimal withdrawCash(String cardNumber, BigDecimal amount) {
        ATMCard atmCard = atmRepository.findByCardNumber(cardNumber).orElse(null);
        if (atmCard != null && atmCard.getAvailableBalance().compareTo(amount) >= 0) {
            atmCard.setAvailableBalance(atmCard.getAvailableBalance().subtract(amount));
            return amount;
        }
        return BigDecimal.ZERO;
    }

    @Transactional
    public BigDecimal depositCash(String cardNumber, BigDecimal amount) {
        ATMCard atmCard = atmRepository.findByCardNumber(cardNumber).orElse(null);
        if (atmCard != null) {
            atmCard.setAvailableBalance(atmCard.getAvailableBalance().add(amount));
            return amount;
        }
        return BigDecimal.ZERO;
    }

    @Transactional
    public boolean changePin(String cardNumber, String newPin) {
        ATMCard atmCard = atmRepository.findByCardNumber(cardNumber).orElse(null);
        if (atmCard != null) {
            atmCard.setPin(passwordEncoder.encode(newPin));
            return true;
        }
        return false;
    }
}