package com.senai.Conta_Bancaria.domain.exception;

public class TransferirParaMesmaContaException extends RuntimeException {
    public TransferirParaMesmaContaException() {

      super("Não é possivel transferir para a mesma conta.");
    }
}
