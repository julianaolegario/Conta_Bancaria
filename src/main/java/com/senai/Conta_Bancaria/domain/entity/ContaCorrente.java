package com.senai.Conta_Bancaria.domain.entity;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ContaCorrente extends Conta {
    private Long limite;
    private int taxa;

}
