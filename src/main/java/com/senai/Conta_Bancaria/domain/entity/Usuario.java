package com.senai.Conta_Bancaria.domain.entity;

import com.senai.Conta_Bancaria.domain.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Table (name = "usuario",//especifica os itens que tem na tabela, modela melhor oq tem nela
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "cpf") // cpf é unico
        }
)
public abstract class Usuario {
    @Id // indica que é uma chave primaria
    @GeneratedValue(strategy = GenerationType.UUID) // gera automaticamente como idenificador universal
    protected String id;

    @NotBlank
    @Column(nullable = false) // tabela nao pode ser nula
    protected String nome;

    @NotBlank
    @Column(nullable = false, unique = true, length = 14)
    protected String cpf; // formato "000.000.000-00" (validação pode ser ampliada)

    @Email
    @NotBlank
    @Column(nullable = false, unique = true)
    protected String email;

    @Column(nullable = false)
    protected boolean ativo = true;

    @NotBlank
    @Column(nullable = false)
    protected String senha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    protected Role role;
}
