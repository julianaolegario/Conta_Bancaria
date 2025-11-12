package com.senai.Conta_Bancaria.domain.exception;

public class PagamentoVencidoException extends RuntimeException {
    public PagamentoVencidoException(String message) {
        super(message);
    }
}
