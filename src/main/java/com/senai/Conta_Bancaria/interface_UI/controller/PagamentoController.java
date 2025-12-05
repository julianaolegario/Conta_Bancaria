package com.senai.Conta_Bancaria.interface_UI.controller;

import com.senai.Conta_Bancaria.application.dto.PagamentoRequestDTO;
import com.senai.Conta_Bancaria.application.dto.PagamentoResponseDTO;
import com.senai.Conta_Bancaria.application.service.PagamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Pagamentos", description = "Operações de criação, consulta e remoção de pagamentos")
@RestController //classe vai responder as requisições HTTP e retornar JSON
@RequestMapping("/api/pagamentos")

public class  PagamentoController {
    private final PagamentoService pagamentoService;

    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    // ---------------------- CRIAR PAGAMENTO ----------------------

    @Operation(
            summary = "Criar um novo pagamento",
            description = "Recebe os dados do pagamento e registra o pagamento no sistema",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = PagamentoRequestDTO.class),
                            examples = @ExampleObject(
                                    name = "Exemplo de criação de pagamento",
                                    value = """
                                            {
                                              "valor": 150.00,
                                              "descricao": "Pagamento de boleto",
                                              "contaOrigem": "12345-6",
                                              "contaDestino": "65432-1"
                                            }
                                            """
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Pagamento criado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos enviados")
            }
    )
    @PostMapping
    public ResponseEntity<PagamentoResponseDTO> criarPagamento(
            @Valid @RequestBody PagamentoRequestDTO pagamentoRequestDTO) {

        PagamentoResponseDTO response = pagamentoService.criarPagamento(pagamentoRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // ---------------------- LISTAR PAGAMENTOS ----------------------

    @Operation(
            summary = "Listar todos os pagamentos",
            description = "Retorna a lista completa de pagamentos cadastrados",
            responses = @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    )
    @GetMapping
    public ResponseEntity<List<PagamentoResponseDTO>> listarPagamentos() {
        return ResponseEntity.ok(pagamentoService.listarPagamentos());
    }

    // ---------------------- BUSCAR POR ID ----------------------

    @Operation(
            summary = "Buscar pagamento por ID",
            description = "Retorna um pagamento específico baseado no ID informado",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pagamento encontrado"),
                    @ApiResponse(responseCode = "404", description = "Pagamento não encontrado")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<PagamentoResponseDTO> buscarPagamentoPorId(@PathVariable String id) {
        return ResponseEntity.ok(pagamentoService.buscarPagamentoPorId(id));
    }

    // ---------------------- DELETAR PAGAMENTO ----------------------

    @Operation(
            summary = "Deletar pagamento",
            description = "Remove um pagamento existente a partir do seu ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Pagamento removido com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Pagamento não encontrado")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPagamento(@PathVariable String id) {
        pagamentoService.deletarPagamento(id);
        return ResponseEntity.noContent().build();
    }
}
