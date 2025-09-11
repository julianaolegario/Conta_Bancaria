package com.senai.Conta_Bancaria.application.dto;

import com.senai.Conta_Bancaria.domain.entity.Cliente;

import java.util.ArrayList;
import java.util.List;

public record ClienteDTO(
        String nome,
        Long cpf,
        List<String> contas
) {
    public static ClienteDTO fromEntity (Cliente cliente){
        if (cliente == null) return null;
        return new ClienteDTO(
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getContas() != null ? cliente.getContas() : List.of()
        );
    }

    public Cliente toEntity(){
       Cliente c = new Cliente();
       c.setNome(this.nome);
       c.setCpf(this.cpf);
       c.setContas(this.contas != null ? new ArrayList<>(this.contas) : new ArrayList<>());
       return c;


    }
}
