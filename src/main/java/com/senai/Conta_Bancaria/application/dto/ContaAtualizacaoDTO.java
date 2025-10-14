package com.senai.Conta_Bancaria.application.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ContaAtualizacaoDTO (
        @NotNull(message = "Saldo é obrigatório")
        BigDecimal saldo,
        BigDecimal limite,
        @NotNull(message = "Rendimento é obrigatório")
        BigDecimal rendimento,
        BigDecimal taxa
){
}
