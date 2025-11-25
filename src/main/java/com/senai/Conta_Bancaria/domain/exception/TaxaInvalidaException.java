package com.senai.Conta_Bancaria.domain.exception;

public class TaxaInvalidaException extends RuntimeException {
    public TaxaInvalidaException(String descricao) {

        super("A taxa informada é inválida: " + descricao);
    }
}
