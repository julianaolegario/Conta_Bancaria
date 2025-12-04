package com.senai.Conta_Bancaria.application.dto;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record  ClienteAtualizadoDTO(
        @NotBlank(message = "Nome é obrigatório")
        String nome,
        @NotBlank(message = "CPF é obrigatório")
        String cpf
) {
}
