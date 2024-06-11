package com.honeywell.atmapp.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "atm_card")
public class ATMCard {
    @Id
    private Long id;
    private String cardNumber;
    private String pin;
    private BigDecimal availableBalance;
}
