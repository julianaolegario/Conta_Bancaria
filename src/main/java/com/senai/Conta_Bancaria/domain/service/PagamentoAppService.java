package com.senai.Conta_Bancaria.domain.service;

import com.senai.Conta_Bancaria.domain.entity.Conta;
import com.senai.Conta_Bancaria.domain.entity.Pagamento;
import com.senai.Conta_Bancaria.domain.repository.ContaRepository;

import java.math.BigDecimal;

public class PagamentoAppService {
    private final ContaRepository contaRepository;
    private final PagamentoDomainService domainService;

    public PagamentoAppService(ContaRepository contaRepository,
                               PagamentoDomainService domainService) {
        this.contaRepository = contaRepository;
        this.domainService = domainService;
    }

    public String efetuarPagamento(Pagamento pagamento) {
        // Busca conta vinculada
        Conta conta = contaRepository.findById(pagamento.getConta().getId())
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        // Calcula o valor total com taxas
        BigDecimal total = domainService.calcularValorTotal(pagamento);

        // Valida o saldo da conta
        domainService.validarSaldo(conta, total);

        // Atualiza saldo e salva no banco
        conta.setSaldo(conta.getSaldo().subtract(total));
        contaRepository.save(conta);

        // Retorna mensagem de confirmação
        return "Pagamento realizado com sucesso! Total debitado: R$ " + total;
    }
}

