package com.senai.Conta_Bancaria.application.dto;

import com.senai.Conta_Bancaria.domain.entity.Cliente;

import java.util.List;

public record ClienteResponseDTO(
        String id,
        String nome,
        String cpf,
        List<ContaResumoDTO> contas // aqui vai vir o numero, cpf e a conta
) {
    public static ClienteResponseDTO fromEntity(Cliente cliente) {
        List<ContaResumoDTO> contas = cliente.getContas().stream()
                .map(ContaResumoDTO::fromEntity)
                .toList();
        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpf(),
                contas
        );
    }
}
