package com.senai.Conta_Bancaria.application.service;

import com.senai.Conta_Bancaria.domain.entity.Conta;
import com.senai.Conta_Bancaria.domain.entity.Pagamento;
import com.senai.Conta_Bancaria.domain.repository.ContaRepository;
import com.senai.Conta_Bancaria.domain.repository.PagamentoRepository;
import com.senai.Conta_Bancaria.domain.service.PagamentoDomainService;
import org.springframework.stereotype.Service;

@Service
public class PagamentoAppService {
    private final PagamentoRepository pagamentoRepository; //para salvar e buscar pagamento no banco
    private final ContaRepository contaRepository; //para acessar dados da conta
    private final PagamentoDomainService pagamentoDomainService; //regras de negocio especificas de pagamento

    public PagamentoAppService(PagamentoRepository pagamentoRepository,
                               ContaRepository contaRepository,
                               PagamentoDomainService pagamentoDomainService) {
        this.pagamentoRepository = pagamentoRepository;
        this.contaRepository = contaRepository;
        this.pagamentoDomainService = pagamentoDomainService;
    }

    public Pagamento realizar(Pagamento pagamento, String id) { //executa o processo de pagamento

        Conta conta = contaRepository.findById(pagamento.getConta().getId())
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        pagamentoDomainService.processarPagamento(pagamento, conta, id); //aqui fica as regras de negocio como, validar saldo, validar cliente, atualizar valores, aplicar taxas, etc

        contaRepository.save(conta); //salva a conta atualizada no banco
        return pagamentoRepository.save(pagamento);// retorna o pagamento ja salvo
    }
}
