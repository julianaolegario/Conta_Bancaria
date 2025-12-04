package com.senai.Conta_Bancaria.application.dto;

import com.senai.Conta_Bancaria.domain.entity.Taxa;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record  TaxaDTO  (
        @NotBlank(message = "Descrição é obrigatória")
        @Schema(description = "Nome da taxa", example = "Tarifa Bancária")
        String descricao,
        @NotNull (message = "Percentual é obrigatório" )
        @Schema(description = "Percentual da taxa aplicada",example = "0.05")
        BigDecimal percentual,
        @NotNull (message = "Valor fixo é obrigatório" )
        @Schema(description = "Valor fixo da taxa aplicada",example = "3,00")
        BigDecimal valorFixo
) {
    public Taxa toEntity(){
        return Taxa.builder ()
                .descricao(this.descricao)
                .percentual(this.percentual)
                .valorFixo(this.valorFixo)
                .build();
    }

}
