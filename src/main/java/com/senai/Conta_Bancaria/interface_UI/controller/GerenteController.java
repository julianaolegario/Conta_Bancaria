package com.senai.Conta_Bancaria.interface_UI.controller;

import com.senai.Conta_Bancaria.application.dto.AuthDTO;
import com.senai.Conta_Bancaria.application.dto.GerenteDTO;
import com.senai.Conta_Bancaria.application.service.AuthService;
import com.senai.Conta_Bancaria.application.service.GerenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gerentes")
@RequiredArgsConstructor
public class GerenteController {
    private final GerenteService service;

    @GetMapping
    public ResponseEntity<List<GerenteDTO>> listarTodosGerentes() {
        List<GerenteDTO> gerentes = service.listarTodosGerentes();
        return ResponseEntity.ok(gerentes);
    }

    @PostMapping
    public ResponseEntity<GerenteDTO> cadastrarGerente(@RequestBody GerenteDTO dto) {
        GerenteDTO gerenteCriado = service.cadastrarGerente(dto);
        return ResponseEntity.ok(gerenteCriado);
    }
}
