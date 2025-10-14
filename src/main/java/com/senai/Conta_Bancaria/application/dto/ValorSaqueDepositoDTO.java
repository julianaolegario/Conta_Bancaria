package com.senai.Conta_Bancaria.application.dto;



import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;


public record ValorSaqueDepositoDTO (
        @NotNull(message = "O valor é obrigatorio")
        BigDecimal valor
){

}
