package com.senai.Conta_Bancaria.domain.exception;

public class RendimentoInvalidoException extends RuntimeException {
    public RendimentoInvalidoException() {

        super("Rendimento deve ser aplicado somente em conta poupança!");
    }
}
