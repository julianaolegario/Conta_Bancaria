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
@Table(name = "pagamento")
public class  Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id; // identificador único

    @ManyToOne
    @JoinColumn(name = "conta_id", nullable = false) // relacionamento com a conta
    private Conta conta;

    @NotBlank
    private String boleto; // identificador do boleto ou referência do serviço

    @NotBlank
    private String descricaoPagamento;

    @NotNull
    private BigDecimal valorPago; // valor principal do pagamento

    @NotNull
    private LocalDateTime dataVencimento; // data e hora do vencimento

    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusPagamento status; // estado do pagamento (SUCESSO, FALHA, etc.)

    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoPagamento tipoPagamento;


    @NotNull
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true) //um pagamento pode ter varias taxas, e se eu salvar ou deletar um pagamento as taxas vao ser salvas e deletadas tambem
    @JoinColumn(name = "pagamento_id") // cria uma chuva estrangeira na tabela Taxa
    private List<Taxa> taxas = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "dispositivo_id", referencedColumnName = "id",  nullable = false) // Relacionamento com Dispositivo IoT
    private DispositivoIOT dispositivoIOT;

    @NotNull
    private BigDecimal valorTotal; // valor principal + taxas

    @NotNull
    private BigDecimal valorTaxas; // soma das taxas aplicadas

}



