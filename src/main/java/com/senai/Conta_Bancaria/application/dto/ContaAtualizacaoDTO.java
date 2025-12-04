package com.senai.Conta_Bancaria.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record  ContaAtualizacaoDTO (

        @Schema(description = "Novo Saldo", example = "1500.00")
        BigDecimal saldo, // Para Conta

        @Schema(description = "Novo Limite", example = "500.00")
        BigDecimal limite, // Para ContaCorrente

        @Schema(description = "Nova Taxa", example = "0.05")
        BigDecimal taxa, // Para ContaCorrente

        @Schema(description = "Novo Rendimento", example = "0.005")
        BigDecimal rendimento // Para ContaPoupanca
) {
        // Você pode usar @Nullable ou não usar @NotNull para permitir que sejam opcionais.
}
