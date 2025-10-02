package com.senai.Conta_Bancaria.application.service;

import com.senai.Conta_Bancaria.application.dto.*;
import com.senai.Conta_Bancaria.domain.entity.Conta;
import com.senai.Conta_Bancaria.domain.entity.ContaCorrente;
import com.senai.Conta_Bancaria.domain.entity.ContaPoupanca;
import com.senai.Conta_Bancaria.domain.repository.ContaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ContaService {
    private final ContaRepository repository;

    @Transactional(readOnly = true)
    public List<ContaResumoDTO> listarTodasContas() {
        return repository.findAllByAtivaTrue().stream()
                .map(ContaResumoDTO::fromEntity).toList();
    }

    @Transactional(readOnly = true)
    public ContaResumoDTO buscarContaPorNumero(String numero) {
        return ContaResumoDTO.fromEntity(
                repository.findByNumeroAndAtivaTrue(numero)
                        .orElseThrow(() -> new RuntimeException("Conta não encontrada"))
        );
    }
    public ContaResumoDTO atualizarConta(String numeroDaConta, ContaAtualizacaoDTO dto){
        Conta conta = repository.findByNumeroAndAtivaTrue(numeroDaConta)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        if (conta instanceof ContaPoupanca poupanca){
            poupanca.setRendimento(dto.rendimento());
        } else if (conta instanceof ContaCorrente corrente) {
            corrente.setLimite(dto.limite());
            corrente.setTaxa(dto.taxa());
        } else {
            throw new RuntimeException("Tipo de conta inválido");
        }
        conta.setSaldo(dto.saldo());

        return ContaResumoDTO.fromEntity(repository.save(conta));
    }
    public void deletarConta(String numeroDaConta){
        Conta conta = repository.findByNumeroAndAtivaTrue(numeroDaConta)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        conta.setAtiva(false);
        repository.save(conta);
    }
    public ContaResumoDTO sacar(String numeroDaConta, ValorSaqueDepositoDTO dto) {
        Conta conta = buscarContaAtivaPorNumero(numeroDaConta);

        conta.sacar(dto.valor());
        return ContaResumoDTO.fromEntity(repository.save(conta));
    }
    public ContaResumoDTO depositar(String numeroDaConta, ValorSaqueDepositoDTO dto) {
        Conta conta = buscarContaAtivaPorNumero(numeroDaConta);

        conta.depositar(dto.valor());
        return ContaResumoDTO.fromEntity(repository.save(conta));
    }
    public ContaResumoDTO transferir(String numeroDaConta, TransferenciaDTO dto){
        Conta contaOrigem = buscarContaAtivaPorNumero(numeroDaConta);
        Conta contaDestino = buscarContaAtivaPorNumero(dto.contaDestino());

        contaOrigem.sacar(dto.valor());
        contaDestino.depositar(dto.valor());

        repository.save(contaDestino);
        return ContaResumoDTO.fromEntity(repository.save(contaOrigem));
    }
    private Conta buscarContaAtivaPorNumero(String numero){
        return repository.findByNumeroAndAtivaTrue(numero)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
    }


}
