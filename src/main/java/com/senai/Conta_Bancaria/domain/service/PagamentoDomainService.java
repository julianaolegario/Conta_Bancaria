package com.senai.Conta_Bancaria.domain.service;

import com.senai.Conta_Bancaria.application.service.AutenticacaoIotService;
import com.senai.Conta_Bancaria.domain.entity.Conta;
import com.senai.Conta_Bancaria.domain.entity.DispositivoIOT;
import com.senai.Conta_Bancaria.domain.entity.Pagamento;
import com.senai.Conta_Bancaria.domain.entity.Taxa;
import com.senai.Conta_Bancaria.domain.enums.StatusPagamento;
import com.senai.Conta_Bancaria.domain.exception.AutenticacaoIoTExpiradaException;
import com.senai.Conta_Bancaria.domain.exception.PagamentoInvalidoException;
import com.senai.Conta_Bancaria.domain.exception.SaldoInsuficienteException;
import com.senai.Conta_Bancaria.domain.exception.TaxaInvalidaException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PagamentoDomainService {
    private final AutenticacaoIotService autenticacaoIotService;

    private static final BigDecimal VALOR_FIXO_DEFAULT = BigDecimal.ZERO; //se a taxa nao tiver valor coloca zero
    private static final BigDecimal PERCENTUAL_DEFAULT = BigDecimal.ZERO;

    private void validarTaxa(Taxa taxa) {

        if (taxa == null) {
            throw new TaxaInvalidaException("Taxa não pode ser nula.");
        }

        if (taxa.getDescricao() == null || taxa.getDescricao().isBlank()) {
            throw new TaxaInvalidaException("Descrição da taxa é obrigatória.");
        }

        BigDecimal percentual = taxa.getPercentual();
        BigDecimal valorFixo = taxa.getValorFixo();

        if (percentual != null && percentual.compareTo(BigDecimal.ZERO) < 0) {
            throw new TaxaInvalidaException("Percentual da taxa não pode ser negativo.");
        }

        if (valorFixo != null && valorFixo.compareTo(BigDecimal.ZERO) < 0) {
            throw new TaxaInvalidaException("Valor fixo da taxa não pode ser negativo.");
        }

        if ((percentual == null || percentual.compareTo(BigDecimal.ZERO) == 0)
                && (valorFixo == null || valorFixo.compareTo(BigDecimal.ZERO) == 0)) {
            throw new TaxaInvalidaException("A taxa deve possuir percentual ou valor fixo maior que zero.");
        }
    }

    public BigDecimal calcularValorTotal(Pagamento pagamento) { //calcula o pagamento com as taxas

        BigDecimal total = pagamento.getValorPago();// Inicializa o total com o valor pago


        List<Taxa> taxas = pagamento.getTaxas() == null ? Collections.emptyList() : pagamento.getTaxas(); // se existirem taxas, pega a lista, caso contrário, usa uma lista vazia


        for (Taxa taxa : taxas) {  // Para cada taxa associada ao pagamento, aplica o percentual e o valor fixo
            validarTaxa(taxa);

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
    public void validarBoleto(Pagamento pagamento) {
        if (pagamento.getDataVencimento() == null) {
            throw new PagamentoInvalidoException("Data de vencimento ausente.");
        }

        if (pagamento.getDataVencimento().isBefore(LocalDateTime.now())) {
            throw new PagamentoInvalidoException("O boleto está vencido.");
        }
    }
    public void processarPagamento(Pagamento pagamento, Conta conta, String id) {
        pagamento.setStatus(StatusPagamento.PROCESSANDO);


        try {
            validarBoleto(pagamento); //valida boleto
        } catch (PagamentoInvalidoException e) {
            pagamento.setStatus(StatusPagamento.BOLETO_VENCIDO);
            throw e;
        }


        BigDecimal total = calcularValorTotal(pagamento);//calcula o total
        pagamento.setValorTotal(total);
        pagamento.setValorTaxas(total.subtract(pagamento.getValorPago()));


        try {
            validarSaldo(conta, total); //valida o saldo
        } catch (SaldoInsuficienteException e) {
            pagamento.setStatus(StatusPagamento.SALDO_INSUFICIENTE);
            throw e;
        }


        DispositivoIOT dispositivo = pagamento.getDispositivoIOT();
        boolean autenticado = autenticacaoIotService.validarOperacao(
                dispositivo.getCodigoSerial(),
                dispositivo.getChavePublica(),
                conta.getCliente().getId()
        );

        if (!autenticado) {
            pagamento.setStatus(StatusPagamento.AUTENTICACAO_EXPIRADA);
            throw new AutenticacaoIoTExpiradaException("Autenticação IoT inválida ou expirada.");
        }


        conta.setSaldo(conta.getSaldo().subtract(total));//debita o valor da conta

        pagamento.setStatus(StatusPagamento.APROVADO); //define status final


        registroTipoPagamento(pagamento);
    }

    private void registroTipoPagamento(Pagamento pagamento) {

        switch (pagamento.getTipoPagamento()) {
            case BOLETO:
                System.out.println("Processando pagamento via Boleto.");
                break;
            case CARTAO_CREDITO:
                System.out.println("Processando pagamento via Cartão de Crédito.");
                break;
            case TRANSFERENCIA:
                System.out.println("Processando pagamento via Transferência.");
                break;
            case PIX:
                System.out.println("Processando pagamento via PIX.");
                break;
            case DEPOSITO:
                System.out.println("Processando pagamento via Depósito.");
                break;
            default:
                pagamento.setStatus(StatusPagamento.FALHA);
                throw new PagamentoInvalidoException("Tipo de pagamento não suportado.");
        }
    }
}


