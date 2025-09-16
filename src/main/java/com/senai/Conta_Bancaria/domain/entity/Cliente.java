package com.senai.Conta_Bancaria.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Entity // cria uma tabela no banco de dados
@Data // cria getters e settters
@Builder // facilita a criacao de um objeto,posso criar aributos de forma mais simplificada
@Table (name = "cliente",//especifica os itens que tem na tabela, modela melhor oq tem nela
        uniqueConstraints = {
         @UniqueConstraint(columnNames = "cpf") // cpf é unico
        }
         )
public class Cliente {
   @Id // indica que é uma chave primaria
   @GeneratedValue(strategy = GenerationType.UUID) // gera automaticamente como idenificador universal
   private String id;
   @Column(nullable = false, length = 120) // tabela nao pode ser nula
   private String nome;
   @Column(nullable = false, length = 11)
   private Long cpf;
   @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL) //um cliente pode ter varias contas
   private List<Conta> contas;
   @Column(nullable = false)
   private Boolean ativa; // conta ativa


}
