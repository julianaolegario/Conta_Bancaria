package com.senai.Conta_Bancaria.application.service;

import com.senai.Conta_Bancaria.application.dto.PagamentoRequestDTO;
import com.senai.Conta_Bancaria.application.dto.PagamentoResponseDTO;
import com.senai.Conta_Bancaria.application.dto.TaxaResponseDTO;
import com.senai.Conta_Bancaria.domain.entity.Conta;
import com.senai.Conta_Bancaria.domain.entity.Pagamento;
import com.senai.Conta_Bancaria.domain.enums.StatusPagamento;
import com.senai.Conta_Bancaria.domain.exception.PagamentoNaoEncontradoException;
import com.senai.Conta_Bancaria.domain.repository.ContaRepository;
import com.senai.Conta_Bancaria.domain.repository.PagamentoRepository;
import com.senai.Conta_Bancaria.domain.service.PagamentoDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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


    public PagamentoResponseDTO criarPagamento(PagamentoRequestDTO request) {

        Pagamento pagamento = new Pagamento(); //cria o objeto de pagamento e preenche com as requisições

        pagamento.setConta(request.conta());
        pagamento.setDescricaoPagamento(request.descricaoPagamento());
        pagamento.setValorPago(new BigDecimal(request.valorPago()));
        pagamento.setDataVencimento(LocalDateTime.parse(request.dataVencimento()));
        pagamento.setStatus(StatusPagamento.PENDENTE);
        pagamento.setTipoPagamento(request.tipoPagamento());

        Pagamento salvo = pagamentoRepository.save(pagamento); //salva o pagamento no banco

        return new PagamentoResponseDTO( //retorna um dto de resposta com os dados do pagamento
                salvo.getId(),
                salvo.getConta(),
                salvo.getDescricaoPagamento(),
                salvo.getValorPago().toString(),
                salvo.getDataVencimento().toString()
        );
    }


    public List<PagamentoResponseDTO> listarPagamentos() { //lista todos os pagamentos cadastrados no sistema
        return pagamentoRepository.findAll().stream()
                .map(p -> new PagamentoResponseDTO(
                        p.getId(),
                        p.getConta(),
                        p.getBoleto(),
                        p.getValorPago().toString(),
                        p.getDataVencimento().toString()
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
                pagamento.getDataVencimento().toString()
        );
    }


    public void deletarPagamento(String id) {
        if (!pagamentoRepository.existsById(id)) {
            throw new PagamentoNaoEncontradoException(id);
        }
        pagamentoRepository.deleteById(id);
    }
}