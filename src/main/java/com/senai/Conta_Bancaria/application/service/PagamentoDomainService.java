package com.senai.Conta_Bancaria.application.service;

import com.senai.Conta_Bancaria.domain.entity.Conta;
import com.senai.Conta_Bancaria.domain.entity.Taxa;
import com.senai.Conta_Bancaria.domain.exception.PagamentoInvalidoException;
import com.senai.Conta_Bancaria.domain.exception.PagamentoVencidoException;
import com.senai.Conta_Bancaria.domain.exception.SaldoInsuficienteException;
import com.senai.Conta_Bancaria.domain.exception.TaxaInvalidaException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Set;

@Service
public class PagamentoDomainService {
    /**
     * Calcula o valor total das taxas para um pagamento dado.
     * percentual é interpretado em % (ex: 2.5 significa 2.5%).
     */
    public BigDecimal calcularValorTaxas(BigDecimal valorBase, Set<Taxa> taxas) {
        if (valorBase == null || valorBase.compareTo(BigDecimal.ZERO) < 0)
            throw new PagamentoInvalidoException("Valor do pagamento inválido.");

        BigDecimal totalTaxas = BigDecimal.ZERO;

        for (Taxa t : taxas) {
            if (t.getPercentual() == null || t.getPercentual().compareTo(BigDecimal.ZERO) < 0)
                throw new TaxaInvalidaException("Taxa percentual inválida para: " + t.getDescricao());

            BigDecimal txPercentual = valorBase.multiply(t.getPercentual()).divide(new BigDecimal("100"));
            BigDecimal txFixa = t.getValorFixo() == null ? BigDecimal.ZERO : t.getValorFixo();
            totalTaxas = totalTaxas.add(txPercentual).add(txFixa);
        }

        return totalTaxas;
    }

    /**
     * Valida se o pagamento pode ser realizado (vencimento, saldo).
     * - boletoVencidoFlag: cliente/app passa controle externo — caso venha true, lança PagamentoVencidoException.
     */
    public void validarAntesDoPagamento(Conta conta, BigDecimal valorTotal, boolean boletoVencido) {
        if (boletoVencido) {
            throw new PagamentoVencidoException("Boleto vencido.");
        }

        if (conta == null) throw new PagamentoInvalidoException("Conta inválida.");

        if (conta.getSaldo() == null || conta.getSaldo().compareTo(valorTotal) < 0) {
            throw new SaldoInsuficienteException("Saldo insuficiente para realização do pagamento.");
        }
    }

    /**
     * Debita o valor total da conta (mutates conta).
     */
    public void debitarConta(Conta conta, BigDecimal valorTotal) {
        BigDecimal novo = conta.getSaldo().subtract(valorTotal);
        conta.setSaldo(novo);
    }

    /**
     * Marca status após tentativa.
     */
    public String statusSucesso() { return "SUCESSO"; }
    public String statusFalha() { return "FALHA"; }
}

