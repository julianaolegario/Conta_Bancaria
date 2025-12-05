package com.senai.Conta_Bancaria.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class  CodigoAutenticacao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id; // Identificador único

    @Column(nullable = false, unique = true, length = 100)
    private String codigo; // Código gerado para autenticação

    @Column(nullable = false)
    private LocalDateTime expiraEm; // Data e hora de expiração do código

    @Column(nullable = false)
    private boolean validado; // Indica se o código foi validado (true/false)

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente; // Relacionamento com o cliente (dono do código)

}
