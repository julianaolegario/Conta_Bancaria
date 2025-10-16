package com.senai.Conta_Bancaria.application.dto;

public class AuthDTO {

    public record LoginRequest(
            String email,
            String senha
    ){}
    public record TokenResponse(
            String token
    ){}
}
