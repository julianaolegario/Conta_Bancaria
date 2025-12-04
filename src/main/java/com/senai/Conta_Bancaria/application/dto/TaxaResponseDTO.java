package com.senai.Conta_Bancaria.application.dto;

import com.senai.Conta_Bancaria.domain.entity.Taxa;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TaxaResponseDTO(
        @NotNull(message = "ID da taxa recebido")
        @Schema(description = "Id da taxa", example = "07")
        Long id,
        @NotBlank(message = "Tipo de taxa")
        @Schema(description = "Nome da taxa", example = "Tarifa Bancária")
        String descricao,
        @NotNull (message = "Percentual é obrigatório" )
        @Schema(description = "Percentual da taxa aplicada",example = "0.05")
        BigDecimal percentual,
        @NotNull (message = "Valor fixo é obrigatório" )
        @Schema(description = "Valor fixo da taxa aplicada",example = "3,00")
        BigDecimal valorFixo
) {
    public static TaxaResponseDTO fromEntity (Taxa t){
        return new TaxaResponseDTO(
                t.getId(),
                t.getDescricao(),
                t.getPercentual(),
                t.getValorFixo()
        );
    }

}
