package com.senai.Conta_Bancaria.application.dto;

import com.senai.Conta_Bancaria.domain.entity.Conta;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record PagamentoResponseDTO (
        @NotNull(message = "ID do pagamento é obrigatório")
        @Schema(description = "ID do pagamento", example = "55")
        Long id,

        @NotNull(message = "Conta é obrigatório")
        @Schema(description = "Especificar a conta", example = "Poupança")
        Conta conta,

        @NotNull(message = "Boleto é obrigatório")
        @Schema(description = "Informar o numero do boleto", example = "2191292992")
        String boleto,

        @NotNull(message = "Valor pago é obrigatório")
        @Schema(description = "Informar o valor pago", example = "R$ 50,00")
        String valorPago,

        @NotNull(message = "Data do pagamento é obrigatório")
        @Schema(description = "Informar a data do pagamento", example = "01-09-2025")
        String dataPagamento
)
{
}
