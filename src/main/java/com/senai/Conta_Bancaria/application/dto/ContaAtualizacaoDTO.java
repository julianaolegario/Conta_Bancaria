package com.senai.Conta_Bancaria.application.dto;

import java.math.BigDecimal;

public record ContaAtualizacaoDTO (
        BigDecimal saldo,
        BigDecimal limite,
        BigDecimal rendimento,
        BigDecimal taxa
){
}
