package com.senai.Conta_Bancaria.domain.exception;

public class EntidadeNaoEncontradaException extends RuntimeException {
    public EntidadeNaoEncontradaException(String entidade) {

        super("Entidade nao encontrada");
    }
}
