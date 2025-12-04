package com.senai.Conta_Bancaria.domain.entity;

import com.senai.Conta_Bancaria.domain.exception.SaldoInsuficienteException;
import com.senai.Conta_Bancaria.domain.exception.TransferirParaMesmaContaException;
import com.senai.Conta_Bancaria.domain.exception.ValoresNegativosException;
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

@GeneratedValue(strategy = GenerationType.IDENTITY)
@Id
private Long id;

@Column(nullable = false, length = 20)
private String numero;

@Column(nullable = false, precision = 19, scale = 2)
private BigDecimal saldo;

@Column(nullable = false)
private boolean ativa; // conta ativa

@ManyToOne(fetch = FetchType.LAZY) // busca uma vez
@JoinColumn(name = "cliente_id", foreignKey = @ForeignKey(name = "fk_conta_cliente"))
private Cliente cliente;

public abstract String getTipo();

public void sacar(BigDecimal valor){
    validarValorMaiorQueZero(valor, "saque");
    if (valor.compareTo(saldo) > 0) {
        throw new SaldoInsuficienteException("saque.");
    }
    saldo = saldo.subtract(valor);
}

    public void depositar(BigDecimal valor) {
        validarValorMaiorQueZero(valor, "deposito");
        saldo = saldo.add(valor);
    }

    protected static void validarValorMaiorQueZero(BigDecimal valor, String operacao) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValoresNegativosException(operacao);
        }
    }
    public void transferir(BigDecimal valor, Conta contaDestino) {
        if (this.id.equals(contaDestino.getId())) {
            throw new TransferirParaMesmaContaException();
        }

        this.sacar(valor);
        contaDestino.depositar(valor);
    }
}
