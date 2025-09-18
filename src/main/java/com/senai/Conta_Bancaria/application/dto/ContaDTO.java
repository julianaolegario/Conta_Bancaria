package com.senai.Conta_Bancaria.application.dto;

import java.math.BigDecimal;

public record ContaDTO(
        String numero,
        String tipo,
        BigDecimal saldo
) {

}
