package com.senai.Conta_Bancaria.application.service;

import com.senai.Conta_Bancaria.application.dto.ClienteRegistroDTO;
import com.senai.Conta_Bancaria.application.dto.ClienteResponseDTO;
import com.senai.Conta_Bancaria.domain.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service// é a cozinha, criar o cliente, permite injetalo em outros componentes
@RequiredArgsConstructor

public class ClienteService{ // service é a camaa entre o controlador e o repository, fornece servicos que podem ser reutilizados por outras partes da aplicação

    private final ClienteRepository repository;
    public ClienteResponseDTO registrarCliente(ClienteRegistroDTO dto){

        var cliente = repository.findByCpfAndAtivoTrue(dto.cpf()).orElseGet(() -> repository.save(dto.toEntity())
        );
        var contas = cliente.getContas();
        var novaConta = dto.contaDTO().toEntity(cliente);

        boolean jaTemTipo = contas.stream().anyMatch(
                c -> c.getClass().equals(novaConta.getClass()) && c.isAtivo());
        if (jaTemTipo)
            throw new RuntimeException("Cliente já possui uma conta desse tipo");
        cliente.getContas().add(novaConta);
        return ClienteResponseDTO.fromEntity(repository.save(cliente));
    }

    public List<ClienteResponseDTO> listarClientesAtivos() {
        return repository.findAllByAtivoTrue().stream()
                .map(ClienteResponseDTO::fromEntity)
                .toList();

    }

    public ClienteResponseDTO buscarClienteAtivoPorCpf(String cpf) {
        var cliente = repository.findByCpfAndAtivoTrue(cpf).orElseThrow(
                () -> new RuntimeException("Cliente não encontrado")
        );
        return ClienteResponseDTO.fromEntity(cliente);
    }
}
