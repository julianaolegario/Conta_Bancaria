package com.senai.Conta_Bancaria.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity // cria uma tabela no banco de dados
@Getter
@Setter
@SuperBuilder // facilita a criacao de um objeto,posso criar aributos de forma mais simplificada
@NoArgsConstructor
public class  Cliente extends Usuario {
   @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL) //um cliente pode ter varias contas
   private List<Conta> contas;

   @OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL)
   private DispositivoIOT dispositivoIoT;



}
