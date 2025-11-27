package com.senai.Conta_Bancaria.domain.service;

import com.senai.Conta_Bancaria.domain.entity.Conta;
import com.senai.Conta_Bancaria.domain.entity.Pagamento;
import com.senai.Conta_Bancaria.domain.entity.Taxa;
import com.senai.Conta_Bancaria.domain.exception.SaldoInsuficienteException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Service
public class PagamentoDomainService {

    private static final BigDecimal VALOR_FIXO_DEFAULT = BigDecimal.ZERO; //se a taxa nao tiver valor coloca zero
    private static final BigDecimal PERCENTUAL_DEFAULT = BigDecimal.ZERO;


    public BigDecimal calcularValorTotal(Pagamento pagamento) { //calcula o pagamento com as taxas

        BigDecimal total = pagamento.getValorPago();// Inicializa o total com o valor pago


        List<Taxa> taxas = pagamento.getTaxas() == null ? Collections.emptyList() : pagamento.getTaxas(); // se existirem taxas, pega a lista, caso contrário, usa uma lista vazia


        for (Taxa taxa : taxas) {  // Para cada taxa associada ao pagamento, aplica o percentual e o valor fixo
            // Usando valores padrão caso os valores sejam nulos
            BigDecimal percentual = taxa.getPercentual() != null ? taxa.getPercentual() : PERCENTUAL_DEFAULT;
            BigDecimal valorFixo = taxa.getValorFixo() != null ? taxa.getValorFixo() : VALOR_FIXO_DEFAULT;


            BigDecimal valorTaxaPercentual = pagamento.getValorPago().multiply(percentual);// coloca o percentual sobre o valor pago


            total = total.add(valorTaxaPercentual).add(valorFixo);// coloca o valor fixo da taxa ao total
        }

        return total; // Retorna o valor total com as taxas aplicadas
    }


    public void validarSaldo(Conta conta, BigDecimal total) { //verifica se a conta tem saldo suficiente
        // Verifica se a conta é nula ou o saldo é nulo
        if (conta == null || conta.getSaldo() == null) {
            throw new IllegalArgumentException("Conta ou saldo não podem ser nulos.");
        } //garante que a conta e o saldo estejam definidos antes de fazer qualquer validação.

        // Compara o saldo da conta com o total a ser pago (considerando taxas)
        if (conta.getSaldo().compareTo(total) < 0) {
            // Se o saldo da conta for insuficiente, lança uma exceção
            throw new SaldoInsuficienteException("Saldo insuficiente para realizar o pagamento.");
        }
    }
}
