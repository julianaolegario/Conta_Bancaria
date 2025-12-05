package com.senai.Conta_Bancaria.application.dto;

import com.senai.Conta_Bancaria.domain.entity.Conta;
import com.senai.Conta_Bancaria.domain.enums.TipoPagamento;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record  PagamentoRequestDTO (
        @NotNull (message = "Conta é obrigatório")
        @Schema(description = "Especificar a conta", example = "Poupança")
        Conta conta,

        @NotNull(message = "Descrição do pagamento é obrigatório")
        @Schema(description = "Informar o numero do pagamento", example = "luz")
        String descricaoPagamento,

        @NotNull(message = "Valor pago é obrigatório")
        @Schema(description = "Informar o valor pago", example = "R$ 50,00")
        String valorPago,

        @NotNull(message = "Data do pagamento é obrigatório")
        @Schema(description = "Informar a data do pagamento", example = "11-10-2025")
        String dataVencimento,

        @NotNull(message = "Tipo de pagamento é obrigatório")
        @Schema(description = "Tipo do pagamento", example = "CARTAO_CREDITO")
        TipoPagamento tipoPagamento
)
{
}
