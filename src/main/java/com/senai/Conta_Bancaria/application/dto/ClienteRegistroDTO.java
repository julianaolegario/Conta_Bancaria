package com.senai.Conta_Bancaria.application.dto;

public record ClienteRegistroDTO(
        String nome,
        String cpf,
        ContaDTO conta,
){
}
