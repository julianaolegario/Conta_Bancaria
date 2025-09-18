package com.senai.Conta_Bancaria.application.dto;

import java.util.List;

public record ClienteResponseDTO(
        String id,
        String nome,
        String cpf,
        List<ClienteRegistroDTO> contas // aqui vai vir o numero, cpf e a conta
) {
}
