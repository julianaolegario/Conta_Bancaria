package com.senai.Conta_Bancaria.domain;

public class UsuarioNaoEncontradoException extends RuntimeException {
    public UsuarioNaoEncontradoException(String message){
        super(message);
    }
}
