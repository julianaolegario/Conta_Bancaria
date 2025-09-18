package com.senai.Conta_Bancaria.application.service;

import com.senai.Conta_Bancaria.application.dto.ClienteRegistroDTO;
import com.senai.Conta_Bancaria.application.dto.ClienteResponseDTO;
import com.senai.Conta_Bancaria.domain.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service// é a cozinha, criar o cliente, permite injetalo em outros componentes
@RequiredArgsConstructor

public class ClienteService{ // service é a camaa entre o controlador e o repository, fornece servicos que podem ser reutilizados por outras partes da aplicação

    private final ClienteRepository repository;
    public ClienteResponseDTO registrarCliente(ClienteRegistroDTO dto){

        var cliente = repository.findByCpfAndAtivoTrue(dto.cpf()).orElseGet(() -> repository.save(dto.toEntity())
        );
        var contas = cliente.getContas();
        var novaConta = dto.conta().toEntity(cliente);

        boolean jaTemTipo = contas.stream()
                .anyMatch(Conta c -> c.getClass().equals(dto.conta() .getClass()) && c.isAtiva());
        return
    }
}
