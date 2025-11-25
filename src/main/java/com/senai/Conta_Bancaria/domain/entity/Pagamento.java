package com.senai.Conta_Bancaria.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.security.core.parameters.P;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
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

    @NotBlank
    private String boleto; // Identificador do boleto ou referência do serviço

    @NotNull
    private BigDecimal valorPago; // Valor principal do pagamento

    @NotNull
    private LocalDateTime dataPagamento; // Data e hora do pagamento

    @NotNull
    private StatusPagamneto status; // Estado do pagamento (SUCESSO, FALHA, etc.)


    @NotNull
    List<Taxa> taxas; // Relacionamento com as taxas aplicadas

}



