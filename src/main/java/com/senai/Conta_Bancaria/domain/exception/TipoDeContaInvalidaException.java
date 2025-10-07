package com.senai.Conta_Bancaria.domain.exception;

public class TipoDeContaInvalidaException extends RuntimeException {
    public TipoDeContaInvalidaException(String tipo) {
        super("Tipo de conta" +tipo+ "invalida. Os tipos validos são: 'CORRENTE' E 'POUPANÇA'.");
    }
}
