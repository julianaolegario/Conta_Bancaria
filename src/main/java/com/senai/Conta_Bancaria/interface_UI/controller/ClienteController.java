package com.senai.Conta_Bancaria.interface_UI.controller;

import com.senai.Conta_Bancaria.application.dto.ClienteAtualizadoDTO;
import com.senai.Conta_Bancaria.application.dto.ClienteRegistroDTO;
import com.senai.Conta_Bancaria.application.dto.ClienteResponseDTO;
import com.senai.Conta_Bancaria.application.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(name = "Cliente", description = "Gerenciamento de clientes da conta bancaria")
@RestController // controlador que trata requisicoes e responde com dados diretamente
@RequestMapping("/api/cliente")
@RequiredArgsConstructor// ve todos os atributos que sao "final", precisam de construtor  e ja cria
public class ClienteController { //  O CONTROLER RECEBE A REQUISICAO REST E DIRECIONA PARA O MAP QUE VAI SER TRATADO, o controller só vai repassar

    private final ClienteService service;

    public ClienteController(ClienteAppService service) {
        this.service = service;
    }

    @Operation(
            summary = "Cadastrar a conta bancaria do cliente",
            description = "Adiciona um novo cliente à base de dados após validações de preço e duração",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ContaBancraiDTO.class),
                            examples = @ExampleObject(name = "Exemplo válido", value = """
                                        {
                                          "descricao": "Troca de óleo",
                                          "preco": 120.0,
                                          "dataInicio": "2025-08-05",
                                          "dataFim": "2025-08-10"
                                        }
                                    """
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Cliente cadastrado com sucesso"),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Erro de validação",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(name =  inválido", value = "\"Preço mínimo do serviço deve ser R$ 50,00\""),
                                            @ExampleObject(name = "Duração excedida", value = "\"Duração do serviço não pode exceder 30 dias\"")
                                    }
                            )
                    )
            }
    )

    @PostMapping
    public ResponseEntity <ClienteResponseDTO> registrarCliente( @Valid @RequestBody ClienteRegistroDTO dto){
        ClienteResponseDTO novoCliente = service.registrarCliente(dto);

    return ResponseEntity.created(URI.create("/api/cliente/cpf/" +novoCliente.cpf())).body(novoCliente);
    }
    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listarClientesAtivos(){
        return ResponseEntity.ok(service.listarClientesAtivos());
    }
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<ClienteResponseDTO>buscarClienteAtivoPorCpf(@PathVariable String cpf){
        return ResponseEntity.ok(service.buscarClienteAtivoPorCpf(cpf));
    }
    @PutMapping("/{cpf}")
    public ResponseEntity<ClienteResponseDTO> atualizarCliente(@PathVariable String cpf,
                                                               @RequestBody ClienteAtualizadoDTO dto) {
        return ResponseEntity.ok(service.atualizarCliente(cpf, dto));
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> deletarCliente(@PathVariable String cpf) {
        service.deletarCliente(cpf);
        return ResponseEntity.noContent().build();
    }
}

