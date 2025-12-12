package com.senai.Conta_Bancaria.application.service;

import com.senai.Conta_Bancaria.application.dto.PagamentoResponseDTO;
import com.senai.Conta_Bancaria.domain.entity.Conta;
import com.senai.Conta_Bancaria.domain.entity.Pagamento;
import com.senai.Conta_Bancaria.domain.exception.PagamentoNaoEncontradoException;
import com.senai.Conta_Bancaria.domain.repository.ContaRepository;
import com.senai.Conta_Bancaria.domain.repository.PagamentoRepository;
import com.senai.Conta_Bancaria.domain.service.PagamentoDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@RequiredArgsConstructor
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;
    private final ContaRepository contaRepository;
    private final PagamentoDomainService pagamentoDomainService;
    private final TaxaService taxaService;

    public Pagamento realizar(Pagamento pagamento, String idCliente) {

        Conta conta = contaRepository.findById(pagamento.getConta().getId())
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        pagamentoDomainService.processarPagamento(pagamento, conta, idCliente); //processa o pagamento aplicando regras de negócio

        contaRepository.save(conta); //salva alterações na conta
        return pagamentoRepository.save(pagamento); //saalva e retorna o pagamento processado
    }




    public List<PagamentoResponseDTO> listarPagamentos() { //lista todos os pagamentos cadastrados no sistema
        return pagamentoRepository.findAll().stream()
                .map(p -> new PagamentoResponseDTO(
                        p.getId(),
                        p.getConta(),
                        p.getBoleto(),
                        p.getValorPago().toString(),
                        p.getDataVencimento().toString(),
                        p.getStatus(),
                        p.getValorTaxas(),
                        p.getValorTotal()
                ))
                .toList();
    }


    public PagamentoResponseDTO buscarPagamentoPorId(String id) {

        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new PagamentoNaoEncontradoException(id));

        return new PagamentoResponseDTO(
                pagamento.getId(),
                pagamento.getConta(),
                pagamento.getBoleto(),
                pagamento.getValorPago().toString(),
                pagamento.getDataVencimento().toString(),
                pagamento.getStatus(),
                pagamento.getValorTaxas(),
                pagamento.getValorTotal()
        );
    }


    public void deletarPagamento(String id) {
        if (!pagamentoRepository.existsById(id)) {
            throw new PagamentoNaoEncontradoException(id);
        }
        pagamentoRepository.deleteById(id);
    }
}