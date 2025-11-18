package com.senai.Conta_Bancaria.domain.service;

import com.senai.Conta_Bancaria.domain.entity.Conta;
import com.senai.Conta_Bancaria.domain.entity.Pagamento;
import com.senai.Conta_Bancaria.domain.entity.Taxa;
import com.senai.Conta_Bancaria.domain.exception.SaldoInsuficienteException;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public class PagamentoDomainService {
    public BigDecimal calcularValorTotal(Pagamento pagamento) {
        BigDecimal total = pagamento.getValorPago();

        List<Taxa> taxas = pagamento.getTaxas() == null ? Collections.emptyList() : pagamento.getTaxas();

        for (Taxa taxa : taxas) {
            BigDecimal percentual = taxa.getPercentual() != null ? taxa.getPercentual() : BigDecimal.ZERO;

            BigDecimal valorTaxa = pagamento.getValorPago()
                    .multiply(percentual);

            total = total.add(valorTaxa);
        }

        return total;
    }

    public void validarSaldo(Conta conta, BigDecimal total) {
        if (conta.getSaldo().compareTo(total) < 0) {
            throw new SaldoInsuficienteException();
        }
    }

}
