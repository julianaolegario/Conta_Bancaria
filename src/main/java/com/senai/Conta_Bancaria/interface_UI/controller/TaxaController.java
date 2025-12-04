package com.senai.Conta_Bancaria.interface_UI.controller;

import com.senai.Conta_Bancaria.application.dto.ClienteRegistroDTO;
import com.senai.Conta_Bancaria.application.dto.TaxaDTO;
import com.senai.Conta_Bancaria.application.dto.TaxaResponseDTO;
import com.senai.Conta_Bancaria.application.service.TaxaService;
import com.senai.Conta_Bancaria.domain.entity.Taxa;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(name = "Taxas", description = "Gerenciamento de taxas aplicadas pelo sistema financeiro")
@RestController
@RequestMapping("/api/taxas")
@RequiredArgsConstructor

public class TaxaController {


    private final TaxaService service;

    @Operation(
            summary = "Cadastrar uma nova taxa",
            description = "Adiciona uma nova taxa ao banco de dados após todas as validações",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ClienteRegistroDTO.class),
                            examples = @ExampleObject(name = "Exemplo válido", value = """
                                        {
                                          "id": 1,
                                          "descricao" : "taxaBoleto",
                                          "Percentual" : 0.5,
                                          "valor fixo" : 3.00
                                        }
                                    """
                            )
                    )
            ),

            responses = {
                    @ApiResponse(responseCode = "201", description = "Taxa cadastrada com sucesso"),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Erro de validação",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(name = "Taxa Inválida", value = "\"Dados inválidos\""),
                                    }
                            )
                    )
            }
    )
    @PostMapping
    public ResponseEntity<TaxaResponseDTO> registrarTaxa(@RequestBody TaxaDTO dto){
        TaxaResponseDTO novaTaxa= service.registrarTaxa(dto);
        return ResponseEntity.created(
                URI.create("/api/taxa/id"+ novaTaxa.id())
        ).body(novaTaxa);
    }

    @Operation(
            summary = "Listar todas as taxas",
            description = "Retorna todas as taxas cadastradas",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
            }
    )
    @GetMapping
    public ResponseEntity<List<TaxaResponseDTO>> listarTodasAsTaxas(){
        return ResponseEntity.ok(service.listarTodasAsTaxas());
    }

    @Operation(
            summary = "Buscar taxa por ID",
            description = "Retorna uma taxa existente a partir do seu id",
            parameters = {
                    @Parameter(name = "id", description = "Id da taxa a ser buscada", example = "1")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Taxa encontrada"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Taxa não encontrada",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "\"Taxa com id 1 não encontrada.\"")
                            )
                    )
            }
    )
    @GetMapping("/id/{id}")

    public ResponseEntity<TaxaResponseDTO> buscarTaxaPorId(@PathVariable String id){
        // Busca a Entidade Taxa
        Taxa taxa = service.buscarTaxaPorId(id);
        // Converte a Entidade para o DTO de Resposta antes de retornar
        TaxaResponseDTO responseDTO = TaxaResponseDTO.fromEntity(taxa);
        return ResponseEntity.ok(responseDTO);
    }

    @Operation(
            summary = "Atualizar uma taxa",
            description = "Atualiza os dados de uma taxa existente com novas informações",
            parameters = {
                    @Parameter(name = "Id", description = "Id da taxa a ser atualizada", example = "1")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ClienteRegistroDTO.class),
                            examples = @ExampleObject(name = "Exemplo de atualização", value = """
                        {
                          {
                                          "id": 1,
                                          "descricao" : "taxaBoleto",
                                          "Percentual" : 0.5,
                                          "valor fixo" : 5.00
                                        }
                    """)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Taxa atualizada com sucesso"),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Erro de validação",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(name = "Taxa inválida", value = "\"Dados inválidos\""),
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Taxa não encontrada",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "\"Taxa com id 1 não encontrada.\"")
                            )
                    )
            }
    )
    @PutMapping("/id/{id}")
    public ResponseEntity<TaxaResponseDTO> atualizarTaxaPorId(@PathVariable String id,
                                                              @RequestBody TaxaDTO dto) {
        return ResponseEntity.ok(service.atualizarTaxa(id, dto));
    }

    @Operation(
            summary = "Deletar uma taxa",
            description = "Remove uma taxa da base de dados a partir do seu id",
            parameters = {
                    @Parameter(name = "Id", description = "Id da taxa a ser deletada", example = "1")
            },
            responses = {
                    @ApiResponse(responseCode = "204", description = "Taxa removida com sucesso"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Taxa não encontrada",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "\"Taxa com id 1 não encontrado.\"")
                            )
                    )
            }
    )
    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deletarTaxa(@PathVariable String id){
        service.deletarTaxa(id);
        return ResponseEntity.noContent().build();
    }
}
