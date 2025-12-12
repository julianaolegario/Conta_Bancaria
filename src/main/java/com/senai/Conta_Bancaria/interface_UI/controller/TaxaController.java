package com.senai.Conta_Bancaria.interface_UI.controller;


import com.senai.Conta_Bancaria.application.dto.TaxaDTO;
import com.senai.Conta_Bancaria.application.dto.TaxaResponseDTO;
import com.senai.Conta_Bancaria.application.service.TaxaService;
import com.senai.Conta_Bancaria.domain.entity.Taxa;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import java.net.URI;
import java.util.List;

@Tag(name = "Taxas", description = "Gerenciamento de taxas aplicadas pelo sistema financeiro")
@RestController
@RequestMapping("/api/taxas")
@RequiredArgsConstructor

public class TaxaController {


    private final TaxaService service;

    @Operation( //cadastra uma nova taxa
            summary = "Cadastrar uma nova taxa",
            description = "Adiciona uma nova taxa ao banco de dados após validações",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = TaxaDTO.class),
                            examples = @ExampleObject(
                                    name = "Exemplo válido",
                                    value = """
                                            {
                                              "descricao": "Taxa de Boleto",
                                              "percentual": 0.05,
                                              "valorFixo": 3.00
                                            }
                                            """
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Taxa cadastrada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro de validação")
            }
    )
    @PostMapping // recebe uma TaxaDTO do cliente, valida e delega para o serviço criar a taxa e retorna 201 Created com a URL da nova taxa para seguir boas práticas REST.

    public ResponseEntity<TaxaResponseDTO> registrarTaxa(@RequestBody TaxaDTO dto){
        TaxaResponseDTO novaTaxa = service.registrarTaxa(dto);

        return ResponseEntity.created(
                URI.create("/api/taxas/" + novaTaxa.id())
        ).body(novaTaxa);
    }

    @Operation(summary = "Listar todas as taxas")
    @GetMapping // consulta todas as taxas cadastradas no sistema e retorna 200 OK com a lista de TaxaResponseDTOs.
    public ResponseEntity<List<TaxaResponseDTO>> listarTodasAsTaxas(){
        return ResponseEntity.ok(service.listarTodasAsTaxas());
    }

    @Operation(
            summary = "Buscar taxa por ID",
            description = "Retorna uma taxa existente a partir do seu id"
    )
    @GetMapping("/{id}") // busca uma taxa específica pelo ID fornecido e converte a entidade para DTO antes de retornar para não expor diretamente a modelagem interna.

    public ResponseEntity<TaxaResponseDTO> buscarTaxaPorId(@PathVariable String id){
        Taxa taxa = service.buscarTaxaPorId(id);
        return ResponseEntity.ok(TaxaResponseDTO.fromEntity(taxa));
    }

    @Operation(
            summary = "Atualizar taxa",
            description = "Atualiza as informações de uma taxa existente"
    )
    @PutMapping("/{id}") // atualiza os dados de uma taxa existente com base no ID e nas informações recebidas e retorna 200 OK com a taxa atualizada.
    public ResponseEntity<TaxaResponseDTO> atualizarTaxaPorId(@PathVariable String id,
                                                              @RequestBody TaxaDTO dto) {
        return ResponseEntity.ok(service.atualizarTaxa(id, dto));
    }

    @Operation(
            summary = "Deletar taxa",
            description = "Remove uma taxa pelo id"
    )
    @DeleteMapping("/{id}") // remove uma taxa pelo ID informado e retorna 204 No Content, indicando que a exclusão foi bem-sucedida sem enviar corpo.
    public ResponseEntity<Void> deletarTaxa(@PathVariable String id){
        service.deletarTaxa(id);
        return ResponseEntity.noContent().build();
    }
}
