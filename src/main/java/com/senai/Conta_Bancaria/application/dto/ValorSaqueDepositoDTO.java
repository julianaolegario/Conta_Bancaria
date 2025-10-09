package com.senai.Conta_Bancaria.application.dto;

import org.antlr.v4.runtime.misc.NotNull;

import java.math.BigDecimal;


public record ValorSaqueDepositoDTO (
        @NotNull
        BigDecimal valor
){

}
