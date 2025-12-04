package com.senai.Conta_Bancaria.interface_UI.controller;

import com.senai.Conta_Bancaria.application.dto.ContaAtualizacaoDTO;
import com.senai.Conta_Bancaria.application.dto.ContaResumoDTO;
import com.senai.Conta_Bancaria.application.dto.TransferenciaDTO;
import com.senai.Conta_Bancaria.application.dto.ValorSaqueDepositoDTO;
import com.senai.Conta_Bancaria.application.service.ContaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.parameters.RequestBody;


import java.util.List;

@Tag(name = "Contas", description = "Gerenciamento de contas bancárias")
@RestController
@RequestMapping("/api/conta")
@RequiredArgsConstructor
public class ContaController {
    private final ContaService service;

    // ---------------- LISTAR CONTAS ----------------

    @Operation(
            summary = "Listar todas as contas",
            description = "Retorna todas as contas cadastradas no sistema",
            responses = @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    )
    @GetMapping
    public ResponseEntity<List<ContaResumoDTO>> listarTodasContas() {
        return ResponseEntity.ok(service.listarTodasContas());
    }

    // ---------------- BUSCAR CONTA ----------------

    @Operation(
            summary = "Buscar conta pelo número",
            description = "Retorna os dados de uma conta através do número",
            parameters = @Parameter(
                    name = "numeroDaConta",
                    description = "Número da conta a ser buscada",
                    example = "12345-6"
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Conta encontrada"),
                    @ApiResponse(responseCode = "404", description = "Conta não encontrada")
            }
    )
    @GetMapping("/{numeroDaConta}")
    public ResponseEntity<ContaResumoDTO> buscarContaPorNumero(@PathVariable String numeroDaConta) {
        return ResponseEntity.ok(service.buscarContaPorNumero(numeroDaConta));
    }

    // ---------------- ATUALIZAR CONTA ----------------

    @Operation(
            summary = "Atualizar conta",
            description = "Atualiza os dados de uma conta existente",
            parameters = @Parameter(
                    name = "numeroDaConta",
                    description = "Número da conta a ser atualizada",
                    example = "12345-6"
            ),
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ContaAtualizacaoDTO.class),
                            examples = @ExampleObject(
                                    name = "Exemplo atualização",
                                    value = """
                                            {
                                              "tipoConta": "CORRENTE",
                                              "limite": 500
                                            }
                                            """
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Conta atualizada"),
                    @ApiResponse(responseCode = "404", description = "Conta não encontrada")
            }
    )
    @PutMapping("/{numeroDaConta}")
    public ResponseEntity<ContaResumoDTO> atualizarConta(
            @PathVariable String numeroDaConta,
            @Valid @org.springframework.web.bind.annotation.RequestBody ContaAtualizacaoDTO dto) {
        return ResponseEntity.ok(service.atualizarConta(numeroDaConta, dto));
    }

    // ---------------- DELETAR CONTA ----------------

    @Operation(
            summary = "Deletar conta",
            description = "Remove uma conta pelo seu número",
            parameters = @Parameter(
                    name = "numeroDaConta",
                    description = "Número da conta a ser deletada",
                    example = "12345-6"
            ),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Conta removida"),
                    @ApiResponse(responseCode = "404", description = "Conta não encontrada")
            }
    )
    @DeleteMapping("/{numeroDaConta}")
    public ResponseEntity<Void> deletarConta(@PathVariable String numeroDaConta) {
        service.deletarConta(numeroDaConta);
        return ResponseEntity.noContent().build();
    }

    // ---------------- SAQUE ----------------

    @Operation(
            summary = "Sacar valor",
            description = "Realiza um saque na conta informada",
            parameters = @Parameter(
                    name = "numeroDaConta",
                    description = "Número da conta",
                    example = "12345-6"
            ),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ValorSaqueDepositoDTO.class),
                            examples = @ExampleObject(
                                    name = "Exemplo saque",
                                    value = "{ \"valor\": 100.0 }"
                            )
                    )
            ),
            responses = @ApiResponse(responseCode = "200", description = "Saque realizado")
    )
    @PostMapping("/{numeroDaConta}/sacar")
    public ResponseEntity<ContaResumoDTO> sacar(
            @PathVariable String numeroDaConta,
            @Valid @org.springframework.web.bind.annotation.RequestBody ValorSaqueDepositoDTO dto) {
        return ResponseEntity.ok(service.sacar(numeroDaConta, dto));
    }

    // ---------------- DEPOSITAR ----------------

    @Operation(
            summary = "Depositar valor",
            description = "Realiza um depósito na conta informada",
            parameters = @Parameter(
                    name = "numeroDaConta",
                    description = "Número da conta",
                    example = "12345-6"
            ),
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ValorSaqueDepositoDTO.class),
                            examples = @ExampleObject(
                                    name = "Exemplo depósito",
                                    value = "{ \"valor\": 250.0 }"
                            )
                    )
            ),
            responses = @ApiResponse(responseCode = "200", description = "Depósito realizado")
    )
    @PostMapping("/{numeroDaConta}/depositar")
    public ResponseEntity<ContaResumoDTO> depositar(
            @PathVariable String numeroDaConta,
            @Valid @org.springframework.web.bind.annotation.RequestBody ValorSaqueDepositoDTO dto) {
        return ResponseEntity.ok(service.depositar(numeroDaConta, dto));
    }

    // ---------------- TRANSFERIR ----------------

    @Operation(
            summary = "Transferir valor",
            description = "Transfere um valor da conta origem para outra conta destino",
            parameters = @Parameter(name = "numeroDaConta", example = "12345-6"),
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = TransferenciaDTO.class),
                            examples = @ExampleObject(
                                    name = "Exemplo transferência",
                                    value = """
                                            {
                                              "contaDestino": "65432-1",
                                              "valor": 300.0
                                            }
                                            """
                            )
                    )
            ),
            responses = @ApiResponse(responseCode = "200", description = "Transferência realizada")
    )
    @PostMapping("/{numeroDaConta}/transferir")
    public ResponseEntity<ContaResumoDTO> transferir(
            @PathVariable String numeroDaConta,
            @Valid @org.springframework.web.bind.annotation.RequestBody TransferenciaDTO dto) {
        return ResponseEntity.ok(service.transferir(numeroDaConta, dto));
    }

    // ---------------- RENDIMENTO ----------------

    @Operation(
            summary = "Aplicar rendimento",
            description = "Aplica rendimento à conta (poupança / investimento)",
            parameters = @Parameter(name = "numeroDaConta", example = "12345-6"),
            responses = @ApiResponse(responseCode = "200", description = "Rendimento aplicado")
    )
    @PostMapping("/{numeroDaConta}/rendimento")
    public ResponseEntity<ContaResumoDTO> aplicarRendimento(@PathVariable String numeroDaConta) {
        return ResponseEntity.ok(service.aplicarRendimento(numeroDaConta));
    }
}



