package com.senai.Conta_Bancaria.domain.exception;

public class ContaMesmoTipoException extends RuntimeException {
    public ContaMesmoTipoException() {

        super("O Cliente ja possui uma conta deste tipo");
    }
}
