package com.senai.Conta_Bancaria.domain.exception;

public class AutenticacaoIotExpiradaException extends RuntimeException {
    public AutenticacaoIotExpiradaException(String message) {
        super(message);
    }
}
