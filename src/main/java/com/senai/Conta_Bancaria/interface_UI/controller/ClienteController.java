package com.senai.Conta_Bancaria.interface_UI.controller;
import com.senai.Conta_Bancaria.application.dto.ClienteRegistroDTO;
import com.senai.Conta_Bancaria.application.dto.ClienteResponseDTO;
import com.senai.Conta_Bancaria.application.service.ClienteService;
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
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import java.net.URI;
import java.util.List;

@Tag(name = "Clientes", description = "Gerenciamento de clientes do banco") //organiza os endpoints
@RestController //descreve oq o endpoint faz
@RequestMapping("/api/cliente")
@RequiredArgsConstructor
public class   ClienteController {


    private final ClienteService service;



    @Operation(
            summary = "Cadastrar um novo cliente",
            description = "Adiciona um novo cliente ao banco de dados após todas as validações",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = com.senai.Conta_Bancaria.application.dto.ClienteRegistroDTO.class),
                            examples = @ExampleObject(name = "Exemplo válido", value = """
                                        {
                                          "id": 1,
                                          "nome": "Juliana",
                                          "cpf": "123.456.789-00",
                                          "email": "juliana@gmail.com",
                                          "senha": "senhaSegura123"
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
                                            @ExampleObject(name = "Cliente Inválido", value = "\"Dados inválidos\""),
                                    }
                            )
                    )
            }
    )
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> registrarCliente(@RequestBody ClienteRegistroDTO dto){
        ClienteResponseDTO novoCliente= service.registrarClienteOuAnexarConta(dto);
        return ResponseEntity.created(
                URI.create("/api/cliente/cpf"+ novoCliente.cpf())
        ).body(novoCliente);
    }

    @Operation(
            summary = "Listar todos os clientes",
            description = "Retorna todos os clientes cadastrados",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
            }
    )
    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listarClientesAtivos(){
        return ResponseEntity.ok(service.listarClientesAtivos());
    }

    @Operation(
            summary = "Buscar cliente por ID",
            description = "Retorna um cliente existente a partir do seu CPF",
            parameters = {
                    @Parameter(name = "CPF", description = "CPF do cliente a ser buscado", example = "123.123.123-00")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Cliente não encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "\"Cliente com cpf 000.000.00-000 não encontrado.\"")
                            )
                    )
            }
    )
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<com.senai.Conta_Bancaria.application.dto.ClienteResponseDTO> buscarClienteAtivoPorCpf(@PathVariable String cpf){
        return ResponseEntity.ok(service.buscarClienteAtivoPorCpf(cpf));
    }

    @Operation(
            summary = "Atualizar um cliente",
            description = "Atualiza os dados de um cliente existente com novas informações",
            parameters = {
                    @Parameter(name = "CPF", description = "CPF do cliente a ser atualizado", example = "123.123.123-00")
            },
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ClienteRegistroDTO.class),
                            examples = @ExampleObject(name = "Exemplo de atualização", value = """
                        {
                          "id": 1,
                          "nome": "Juliana Santos",
                          "cpf": "123.456.789-00",
                          "email": juliana@gmail.com",
                          "senha": "novaSenhaSegura123"
                        }
                    """)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Erro de validação",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(name = "Cliente inválido", value = "\"Dados inválidos\""),
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Cliente não encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "\"Cliente com CPF 000.000.000-0 não encontrado.\"")
                            )
                    )
            }
    )
    @PutMapping("/cpf/{cpf}")
    public ResponseEntity<ClienteResponseDTO> atualizarCliente(@PathVariable String cpf,
                                                               @RequestBody ClienteRegistroDTO dto) {
        return ResponseEntity.ok(service.atualizarCliente(cpf, dto));
    }

    @Operation(
            summary = "Deletar um cliente",
            description = "Remove um cliente da base de dados a partir do seu CPF",
            parameters = {
                    @Parameter(name = "CPF", description = "CPF do cliente a ser deletado", example = "1")
            },
            responses = {
                    @ApiResponse(responseCode = "204", description = "cliente removido com sucesso"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Cliente não encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "\"Cliente com CPF 000.000.000-00 não encontrado.\"")
                            )
                    )
            }
    )
    @DeleteMapping("/cpf/{cpf}")
    public ResponseEntity<Void> deletarCliente(@PathVariable String cpf){
        service.deletarCliente(cpf);
        return ResponseEntity.noContent().build();
    }
}

