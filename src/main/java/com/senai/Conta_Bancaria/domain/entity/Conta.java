package com.senai.Conta_Bancaria.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity // gera uma tabela no banco de dados
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // estrategia que vai usar e em uma unica tabela vai ter mais de um tipo(ex em conta vai ter conta corrente e conta poupanca)
@DiscriminatorColumn(name = "tipo_conta", discriminatorType = DiscriminatorType.STRING) // distigue os dois tipos de conta que vai ter na tabela conta
@Table(name = "conta",
            uniqueConstraints = {
                @UniqueConstraint(name = "uk_conta_numero", columnNames = "numero"),
                @UniqueConstraint(name = "uk_cliente_tipo", columnNames = {"cliente_id", "tipo_conta"})
            })
@Data
@SuperBuilder // facilita a construcao de um objeto e é super pq é uma heranca
@NoArgsConstructor // obrigatorio se tem o builder
public abstract class Conta {

@GeneratedValue(strategy = GenerationType.UUID)
@Id
private String id;

@Column(nullable = false, length = 20)
private String numero;

@Column(nullable = false, precision = 4)
private BigDecimal saldo;

@Column(nullable = false)
private boolean ativa; // conta ativa

@ManyToOne(fetch = FetchType.LAZY) // busca uma vez
@JoinColumn(name = "cliente_id", foreignKey = @ForeignKey(name = "fk_conta_cliente"))
private Cliente cliente;

public abstract String getTipo();

public void sacar(BigDecimal valor){
    if(valor.compareTo(BigDecimal.ZERO) <= 0){
        throw new IllegalArgumentException("O valor de saque deve ser positivo.");
    }
    if (valor.compareTo(saldo) >0) {
    throw new IllegalArgumentException("Saldo insuficiente para saque");
    }
    saldo = saldo.subtract(valor);
}

    public void depositar(BigDecimal valor) {
        validarValorMaiorQueZero(valor);
        saldo = saldo.subtract(valor);
    }

    private static void validarValorMaiorQueZero(BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor do deposito deve ser positivo")
        }
    }
}
