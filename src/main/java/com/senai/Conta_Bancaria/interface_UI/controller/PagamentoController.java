package com.senai.Conta_Bancaria.interface_UI.controller;

import com.senai.Conta_Bancaria.application.dto.PagamentoRequestDTO;
import com.senai.Conta_Bancaria.application.dto.PagamentoResponseDTO;
import com.senai.Conta_Bancaria.application.service.PagamentoService;
import com.senai.Conta_Bancaria.domain.entity.Pagamento;
import com.senai.Conta_Bancaria.domain.enums.StatusPagamento;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
                                              "conta": { "id": "123" },
                                                            "boleto": "2191292992",
                                                            "descricaoPagamento": "Pagamento de luz",
                                                            "valorPago": "50.00",
                                                            "dataVencimento": "11-10-2025",
                                                            "tipoPagamento": "CARTAO_CREDITO"
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
            @Valid @RequestBody PagamentoRequestDTO dto) {

        Pagamento pagamento = new Pagamento();
        pagamento.setConta(dto.conta());
        pagamento.setBoleto(dto.boleto());
        pagamento.setDescricaoPagamento(dto.descricaoPagamento());
        pagamento.setValorPago(new BigDecimal(dto.valorPago()));
        pagamento.setDataVencimento(LocalDateTime.parse(dto.dataVencimento()));
        pagamento.setStatus(StatusPagamento.PENDENTE);
        pagamento.setTipoPagamento(dto.tipoPagamento());

        Pagamento salvo = pagamentoService.realizar(pagamento, "idClienteAqui");

        PagamentoResponseDTO response = new PagamentoResponseDTO(
                salvo.getId(),
                salvo.getConta(),
                salvo.getBoleto(),
                salvo.getValorPago().toString(),
                salvo.getDataVencimento().toString(),
                salvo.getStatus(),
                salvo.getValorTaxas(),
                salvo.getValorTotal()
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Listar todos os pagamentos",
            description = "Retorna todos os pagamentos cadastrados",
            responses = @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    )
    @GetMapping
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
    @GetMapping("/{id}")
    public ResponseEntity<PagamentoResponseDTO> buscarPagamentoPorId(@PathVariable String id) {
        return ResponseEntity.ok(pagamentoService.buscarPagamentoPorId(id));
    }

    @Operation(
            summary = "Deletar pagamento",
            description = "Remove um pagamento existente a partir do seu ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Pagamento deletado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Pagamento não encontrado")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPagamento(@PathVariable String id) {
        pagamentoService.deletarPagamento(id);
        return ResponseEntity.noContent().build();
    }
}