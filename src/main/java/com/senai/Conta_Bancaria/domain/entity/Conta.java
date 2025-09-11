package com.senai.Conta_Bancaria.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data //gera getters e setters
@MappedSuperclass
@Entity // gera uma tabela no banco de dados
public abstract class Conta {
@GeneratedValue(strategy = GenerationType.UUID)
@Id
private String numero;
private Long saldo;

}
