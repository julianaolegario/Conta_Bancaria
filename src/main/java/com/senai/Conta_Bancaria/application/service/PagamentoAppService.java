package com.senai.Conta_Bancaria.application.service;

import com.senai.Conta_Bancaria.domain.entity.Conta;
import com.senai.Conta_Bancaria.domain.entity.Pagamento;
import com.senai.Conta_Bancaria.domain.repository.ContaRepository;
import com.senai.Conta_Bancaria.domain.repository.PagamentoRepository;
import com.senai.Conta_Bancaria.domain.service.PagamentoDomainService;
import org.springframework.stereotype.Service;

@Service
public class PagamentoAppService {
    private final PagamentoRepository pagamentoRepository;
    private final ContaRepository contaRepository;
    private final PagamentoDomainService pagamentoDomainService;

    public PagamentoAppService(PagamentoRepository pagamentoRepository,
                               ContaRepository contaRepository,
                               PagamentoDomainService pagamentoDomainService) {
        this.pagamentoRepository = pagamentoRepository;
        this.contaRepository = contaRepository;
        this.pagamentoDomainService = pagamentoDomainService;
    }

    public Pagamento realizar(Pagamento pagamento, String idCliente) {

        Conta conta = contaRepository.findById(pagamento.getConta().getId())
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        pagamentoDomainService.processarPagamento(pagamento, conta, idCliente);

        contaRepository.save(conta);
        return pagamentoRepository.save(pagamento);
    }
}
