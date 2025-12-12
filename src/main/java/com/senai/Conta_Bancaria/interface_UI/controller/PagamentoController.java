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
    @PostMapping // endpoint para criar um pagamento, valida o DTO enviado pelo cliente e delega a criação ao serviço, retorna 201 Created com o pagamento criado.
    public ResponseEntity<PagamentoResponseDTO> criarPagamento(
            @Valid @RequestBody PagamentoRequestDTO pagamentoRequestDTO) {

        PagamentoResponseDTO response = pagamentoService.criarPagamento(pagamentoRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @Operation(
            summary = "Listar todos os pagamentos",
            description = "Retorna a lista completa de pagamentos cadastrados",
            responses = @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    )
    @GetMapping   // endpoint para listar todos os pagamentos, retorna 200 OK com a lista de pagamentos.
    public ResponseEntity<List<PagamentoResponseDTO>> listarPagamentos() {
        return ResponseEntity.ok(pagamentoService.listarPagamentos());
    }


    @Operation(
            summary = "Buscar pagamento por ID",
            description = "Retorna um pagamento específico baseado no ID informado",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pagamento encontrado"),
                    @ApiResponse(responseCode = "404", description = "Pagamento não encontrado")
            }
    )
    @GetMapping("/{id}") // endpoint para buscar um pagamento específico pelo ID, retorna 200 OK se encontrado, 404 se não existir.
    public ResponseEntity<PagamentoResponseDTO> buscarPagamentoPorId(@PathVariable String id) {
        return ResponseEntity.ok(pagamentoService.buscarPagamentoPorId(id));
    }


    @Operation(
            summary = "Deletar pagamento",
            description = "Remove um pagamento existente a partir do seu ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Pagamento removido com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Pagamento não encontrado")
            }
    )
    @DeleteMapping("/{id}") // endpoint para remover um pagamento pelo ID, retorna 204 No Content para indicar que a exclusão foi bem-sucedida.
    public ResponseEntity<Void> deletarPagamento(@PathVariable String id) {
        pagamentoService.deletarPagamento(id);
        return ResponseEntity.noContent().build();
    }
}
