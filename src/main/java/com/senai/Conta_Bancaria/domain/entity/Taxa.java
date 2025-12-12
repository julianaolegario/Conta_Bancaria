package com.senai.Conta_Bancaria.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity
@Data
@SuperBuilder
public class  Taxa {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id; // identificador único da taxa

    @Column(nullable = false, length = 100)
    private String descricao; // nome da taxa (ex: IOF, Tarifa Bancária, etc.)

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal percentual; // percentual da taxa (ex: 2.5%)

    @Column(precision = 10, scale = 2)
    private BigDecimal valorFixo; // valor adicional fixo (opcional)
}
