package com.senai.Conta_Bancaria.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransferenciaDTO(
        @NotBlank(message = "O campo contaDestino é obrigatório")
        String contaDestino,
        @NotNull(message = "O valor é obrigatório")
        BigDecimal valor
)  {
}
