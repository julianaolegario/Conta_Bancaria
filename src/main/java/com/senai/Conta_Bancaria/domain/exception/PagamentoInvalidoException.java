package com.senai.Conta_Bancaria.domain.exception;

public class PagamentoInvalidoException extends RuntimeException {
    public PagamentoInvalidoException() {

        super("O pagamento informado é inválido");
    }
}
