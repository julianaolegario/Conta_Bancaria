package com.senai.Conta_Bancaria.domain.exception;

public class PagamentoInvalidoException extends RuntimeException {
    public PagamentoInvalidoException(String message) {

        super(message);
    }
}
