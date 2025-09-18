package com.senai.Conta_Bancaria.interface_UI;

import com.senai.Conta_Bancaria.application.dto.ClienteRegistroDTO;
import com.senai.Conta_Bancaria.application.dto.ClienteResponseDTO;
import com.senai.Conta_Bancaria.application.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController // controlador que trata requisicoes e responde com dados diretamente
@RequestMapping("/api/cliente")
@RequiredArgsConstructor// ve todos os atributos que sao "final", precisam de construtor  e ja cria
public class ClienteController { // o controller só vai repassar

    private final ClienteService service;

    @PostMapping
    public ClienteResponseDTO registrarCliente(@RequestBody ClienteRegistroDTO dto){
    return service.registrarCliente(dto);
    }
}

