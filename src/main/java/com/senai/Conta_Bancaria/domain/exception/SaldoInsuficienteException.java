package com.senai.Conta_Bancaria.domain.exception;

public class SaldoInsuficienteException extends RuntimeException {
    public SaldoInsuficienteException(String operacao) {

        super("Saldo insuficiente para realizar a operação de" + operacao);
    }
}
