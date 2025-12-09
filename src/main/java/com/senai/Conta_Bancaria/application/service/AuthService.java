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
    private final UsuarioRepository usuarios; //consulta usuarios no banco de dados
    private final PasswordEncoder encoder; //criptografa senhas e compara senhas digitadas com senhas salvas no banco
    private final JwtService jwt; // serviço personalizado que gera tokens

    public String login(AuthDTO.LoginRequest req) { //recebe um email e senha e retorna o token gerado
        Usuario usuario = usuarios.findByEmail(req.email())
                .orElseThrow(() ->  new UsuarioNaoEncontradoException("Usuário não encontrado"));

        if (!encoder.matches(req.senha(), usuario.getSenha())) {
            throw new BadCredentialsException("Credenciais inválidas");
        }

        return jwt.generateToken(usuario.getEmail(), usuario.getRole().name());
    }
}
