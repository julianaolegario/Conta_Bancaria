package com.senai.Conta_Bancaria.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity
@Data
@SuperBuilder
public class Taxa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador único da taxa

    @Column(nullable = false, length = 100)
    private String descricao; // Nome da taxa (ex: IOF, Tarifa Bancária, etc.)

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal percentual; // Percentual da taxa (ex: 2.5%)

    @Column(precision = 10, scale = 2)
    private BigDecimal valorFixo; // Valor adicional fixo (opcional)
}
