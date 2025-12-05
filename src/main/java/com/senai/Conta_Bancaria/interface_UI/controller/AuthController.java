package com.senai.Conta_Bancaria.interface_UI.controller;

import com.senai.Conta_Bancaria.application.dto.AuthDTO;
import com.senai.Conta_Bancaria.application.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@Tag(name = "Autenticação", description = "Gerenciamento de login e emissao de tokens JWT ")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService auth;
    @Operation(
            summary = "Realizar login",
            description = "Autentica um usuário e retorna um token JWT válido",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = AuthDTO.LoginRequest.class),
                            examples = @ExampleObject(
                                    name = "Exemplo de login",
                                    value = """
                                            {
                                              "email": "usuario@gmail.com",
                                              "senha": "senhaSegura123"
                                            }
                                            """
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Login realizado com sucesso",
                            content = @Content(
                                    schema = @Schema(implementation = AuthDTO.TokenResponse.class),
                                    examples = @ExampleObject(
                                            name = "Exemplo de token",
                                            value = """
                                                    {
                                                      "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
                                                    }
                                                    """
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "401", description = "Credenciais inválidas",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "\"Email ou senha incorretos\"")
                            )
                    )
            }
    )

    @PostMapping("/login")
    public ResponseEntity<AuthDTO.TokenResponse> login(@RequestBody AuthDTO.LoginRequest req) {
        String token = auth.login(req);
        return ResponseEntity.ok(new AuthDTO.TokenResponse(token));
    }
}
