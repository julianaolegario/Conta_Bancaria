package com.senai.Conta_Bancaria.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@DiscriminatorValue("POUPANCA")
@EqualsAndHashCode(callSuper = true)
public class ContaPoupanca extends Conta{

    @Column(precision = 5)
    private BigDecimal rendimento;


    @Override
    public String getTipo() {
        return "POUPANCA";
    }
}
