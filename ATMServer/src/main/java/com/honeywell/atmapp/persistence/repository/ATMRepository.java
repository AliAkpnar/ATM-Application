package com.honeywell.atmapp.persistence.repository;

import com.honeywell.atmapp.persistence.entity.ATMCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ATMRepository  extends JpaRepository<ATMCard, Long> {
    Optional<ATMCard> findByCardNumber(String cardNumber);
}
