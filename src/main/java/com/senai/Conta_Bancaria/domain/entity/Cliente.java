package com.senai.Conta_Bancaria.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Cliente {
   private String nome;
   private Long cpf;
   @Column(name = "contas")
   private List<String> contas;


}
