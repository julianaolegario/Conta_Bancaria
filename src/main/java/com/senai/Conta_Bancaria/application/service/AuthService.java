package com.senai.Conta_Bancaria.application.service;

import com.senai.Conta_Bancaria.application.dto.AuthDTO;
import com.senai.Conta_Bancaria.domain.UsuarioNaoEncontradoException;
import com.senai.Conta_Bancaria.domain.entity.Usuario;
import com.senai.Conta_Bancaria.domain.repository.UsuarioRepository;
import com.senai.Conta_Bancaria.infrastructure.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class  AuthService {
    private final UsuarioRepository usuarios;
    private final PasswordEncoder encoder;
    private final JwtService jwt;

    public String login(AuthDTO.LoginRequest req) {
        Usuario usuario = usuarios.findByEmail(req.email())
                .orElseThrow(() ->  new UsuarioNaoEncontradoException("Usuário não encontrado"));

        if (!encoder.matches(req.senha(), usuario.getSenha())) {
            throw new BadCredentialsException("Credenciais inválidas");
        }

        return jwt.generateToken(usuario.getEmail(), usuario.getRole().name());
    }
}
