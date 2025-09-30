package com.senai.Conta_Bancaria.application.dto;

import java.math.BigDecimal;

public record TransferenciaDTO(
        String contaDestino,
        BigDecimal valor
)  {
}
