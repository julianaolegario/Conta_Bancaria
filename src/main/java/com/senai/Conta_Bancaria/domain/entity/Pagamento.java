package com.senai.Conta_Bancaria.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.parameters.P;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id; // Identificador único

    @ManyToOne
    @JoinColumn(name = "conta_id", nullable = false) // Relacionamento com a Conta
    private Conta conta;

    @Column(nullable = false)
    private String boleto; // Identificador do boleto ou referência do serviço

    @Column(nullable = false)
    private BigDecimal valorPago; // Valor principal do pagamento

    @Column(nullable = false)
     private String dataPagamento; // Data e hora do pagamento

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Enum status; // Estado do pagamento (SUCESSO, FALHA, etc.)

    @ManyToMany
    @JoinTable(
            name = "pagamento_taxa",
            joinColumns = @JoinColumn(name = "pagamento_id"),
            inverseJoinColumns = @JoinColumn(name = "taxa_id")
    )
    private Set<Taxa> taxas; // Relacionamento com as taxas aplicadas

}



