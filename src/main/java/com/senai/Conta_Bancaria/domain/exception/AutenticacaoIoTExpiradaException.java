package com.senai.Conta_Bancaria.domain.exception;

public class AutenticacaoIoTExpiradaException extends RuntimeException {
    public AutenticacaoIoTExpiradaException(String message) {
        super(message);
    }
}
