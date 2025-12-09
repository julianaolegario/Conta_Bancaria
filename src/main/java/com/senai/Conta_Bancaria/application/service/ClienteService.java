package com.senai.Conta_Bancaria.application.service;

import com.senai.Conta_Bancaria.application.dto.ClienteRegistroDTO;
import com.senai.Conta_Bancaria.application.dto.ClienteResponseDTO;
import com.senai.Conta_Bancaria.domain.entity.Cliente;
import com.senai.Conta_Bancaria.domain.exception.ContaMesmoTipoException;
import com.senai.Conta_Bancaria.domain.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service// é a cozinha, criar o cliente, permite injetalo em outros componentes
@RequiredArgsConstructor

public class  ClienteService{ // service é a cama entre o controlador e o repository, fornece servicos que podem ser reutilizados por outras partes da aplicação

    private final ClienteRepository repository;
    private final PasswordEncoder passwordEncoder;


    @PreAuthorize("hasAnyRole ('GERENTE', 'ADMIN')") // especifica que só o gerente pode registrar o cliente
    public ClienteResponseDTO registrarClienteOuAnexarConta(ClienteRegistroDTO dto){

        var cliente = repository.findByCpfAndAtivoTrue(dto.cpf()).orElseGet(() -> repository.save(dto.toEntity())
        );
        var contas = cliente.getContas();
        var novaConta = dto.contaDTO().toEntity(cliente);

        boolean jaTemTipo = contas.stream()
                .anyMatch(c -> c.getClass().equals(novaConta.getClass()) && c.isAtiva());
        if (jaTemTipo)
            throw new ContaMesmoTipoException();

        cliente.getContas().add(novaConta);
        cliente.setSenha(passwordEncoder.encode(dto.senha()));

        return ClienteResponseDTO.fromEntity(repository.save(cliente));
    }

    @PreAuthorize("hasAnyRole('GERENTE', 'ADMIN')") // especifica que só o gerente pode listar clientes ativos
    public List<ClienteResponseDTO> listarClientesAtivos() {
        return repository.findAllByAtivoTrue().stream()
                .map(ClienteResponseDTO::fromEntity)
                .toList();

    }
    @PreAuthorize("hasAnyRole ('GERENTE', 'ADMIN')") // especifica que só o gerente pode buscar cliente ativo por cpf
    public ClienteResponseDTO buscarClienteAtivoPorCpf(String cpf) {
        var cliente = buscarClientePorCpfEAtivo(cpf);
        return ClienteResponseDTO.fromEntity(cliente);
    }

    @PreAuthorize("hasAnyRole ('GERENTE', 'ADMIN')") // especifica que só o gerente pode
    private Cliente buscarClientePorCpfEAtivo(String cpf) {
        var cliente = repository.findByCpfAndAtivoTrue(cpf).orElseThrow(
                () -> new RuntimeException("Cliente não encontrado.")
        );
        return cliente;
    }

    @PreAuthorize("hasAnyRole ('GERENTE', 'ADMIN')") // especifica que só o gerente pode atualizar cliente
    public ClienteResponseDTO atualizarCliente(String cpf, ClienteRegistroDTO dto) {
        var cliente = buscarClientePorCpfEAtivo(cpf);

        cliente.setNome(dto.nome());
        cliente.setCpf(dto.cpf());

        return ClienteResponseDTO.fromEntity(repository.save(cliente));
    }

    @PreAuthorize("hasAnyRole ('GERENTE', 'ADMIN')") // especifica que só o gerente pode deletar o  cliente
    public void deletarCliente(String cpf) {
        var cliente = buscarClientePorCpfEAtivo(cpf);
        cliente.setAtivo(false);

        cliente.getContas().forEach(
                conta -> conta.setAtiva(false)
        );
        repository.save(cliente);
    }
}

