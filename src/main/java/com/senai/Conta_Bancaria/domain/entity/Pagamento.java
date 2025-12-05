package com.senai.Conta_Bancaria.domain.entity;

import com.senai.Conta_Bancaria.domain.enums.StatusPagamento;
import com.senai.Conta_Bancaria.domain.enums.TipoPagamento;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.security.core.parameters.P;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class  Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id; // Identificador único

    @ManyToOne
    @JoinColumn(name = "conta_id", nullable = false) // Relacionamento com a Conta
    private Conta conta;

    @NotBlank
    private String boleto; // Identificador do boleto ou referência do serviço

    @NotBlank
    private String descricaoPagamento;

    @NotNull
    private BigDecimal valorPago; // Valor principal do pagamento

    @NotNull
    private LocalDateTime dataPagamento; // Data e hora do pagamento

    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusPagamento status; // Estado do pagamento (SUCESSO, FALHA, etc.)

    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoPagamento tipoPagamento;


    @NotNull
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true) //um pagamento pode ter varias taxas, e se eu salvar ou deletar um pagamento as taxas vao ser salvas e deletadas tambem
    @JoinColumn(name = "pagamento_id") // cria uma chuva estrangeira na tabela Taxa
    private List<Taxa> taxas = new ArrayList<>();

}



