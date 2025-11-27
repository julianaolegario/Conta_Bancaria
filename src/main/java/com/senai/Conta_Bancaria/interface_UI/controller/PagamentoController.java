package com.senai.Conta_Bancaria.interface_UI.controller;

import com.senai.Conta_Bancaria.application.dto.PagamentoRequestDTO;
import com.senai.Conta_Bancaria.application.dto.PagamentoResponseDTO;
import com.senai.Conta_Bancaria.application.service.PagamentoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //classe vai responder as requisições HTTP e retornar JSON
@RequestMapping("/api/pagamentos")

public class PagamentoController {
    private final PagamentoService pagamentoService;

    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @PostMapping //recebe requisiçoes post
    public ResponseEntity<PagamentoResponseDTO> criarPagamento(
            @Valid @RequestBody PagamentoRequestDTO pagamentoRequestDTO) {


        PagamentoResponseDTO response = pagamentoService.criarPagamento(pagamentoRequestDTO); // chama o service para criar o pagamento


        return new ResponseEntity<>(response, HttpStatus.CREATED); // retorna o pagamento  com status HTTP 201
    }


    @GetMapping //retorna a lista de dto de pagamentos
    public ResponseEntity<List<PagamentoResponseDTO>> listarPagamentos() {
        List<PagamentoResponseDTO> pagamentos = pagamentoService.listarPagamentos();
        return ResponseEntity.ok(pagamentos); // status 200 OK
    }


    @GetMapping("/{id}") //pagamento por id
    public ResponseEntity<PagamentoResponseDTO> buscarPagamentoPorId(@PathVariable Long id) {
        PagamentoResponseDTO pagamento = pagamentoService.buscarPagamentoPorId(id);
        return ResponseEntity.ok(pagamento); // status 200 OK
    }


    @DeleteMapping("/{id}") //deleta pagamento
    public ResponseEntity<Void> deletarPagamento(@PathVariable Long id) {
        pagamentoService.deletarPagamento(id);
        return ResponseEntity.noContent().build(); // status 204 No Content
    }
}
