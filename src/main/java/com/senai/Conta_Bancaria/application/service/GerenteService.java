package com.senai.Conta_Bancaria.application.service;

import com.senai.Conta_Bancaria.application.dto.GerenteDTO;
import com.senai.Conta_Bancaria.domain.entity.Gerente;
import com.senai.Conta_Bancaria.domain.enums.Role;
import com.senai.Conta_Bancaria.domain.repository.GerenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.PasswordAuthentication;
import java.util.List;

@Service
@RequiredArgsConstructor
public class  GerenteService {

    private final GerenteRepository gerenteRepository;
    private final PasswordEncoder encoder;

    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')") //restrição de acesso
    public List<GerenteDTO> listarTodosGerentes() { //lista todos os gerentes cadastrados no sistema
        return gerenteRepository.findAll().stream()
                .map(GerenteDTO::fromEntity)
                .toList();
    }


    @PreAuthorize("hasAnyRole('ADMIN')") //apenas admin pode cadastrar um gerente
    public GerenteDTO cadastrarGerente(GerenteDTO dto) {
        Gerente entity = dto.toEntity();
        entity.setSenha(encoder.encode(dto.senha()));
        entity.setRole(Role.GERENTE);
        gerenteRepository.save(entity);
        return GerenteDTO.fromEntity(entity);
    }
}
