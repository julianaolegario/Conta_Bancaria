package com.senai.Conta_Bancaria.application.service;

import com.senai.Conta_Bancaria.application.dto.TaxaDTO;
import com.senai.Conta_Bancaria.application.dto.TaxaResponseDTO;
import com.senai.Conta_Bancaria.domain.entity.Taxa;
import com.senai.Conta_Bancaria.domain.exception.EntidadeNaoEncontradaException;
import com.senai.Conta_Bancaria.domain.repository.TaxaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class TaxaService {

    private final TaxaRepository repository;

    public TaxaResponseDTO registrarTaxa(TaxaDTO dto) {
        var taxa = dto.toEntity();
        var taxaSalva = repository.save(taxa);

        return TaxaResponseDTO.fromEntity(taxaSalva);

    }


    public List<TaxaResponseDTO> listarTodasAsTaxas() {

        return repository.findAll().stream()
                .map(TaxaResponseDTO::fromEntity)
                .toList();

    }

    public Taxa buscarTaxaPorId(String id) {

        return repository.findById(id).orElseThrow(
                () -> new EntidadeNaoEncontradaException("Taxa com ID " + id)

        );

    }

    public TaxaResponseDTO atualizarTaxa(String id, TaxaDTO dto) {
        var taxa = buscarTaxaPorId(id);
        taxa.setDescricao(dto.descricao());
        taxa.setPercentual(dto.percentual());
        taxa.setValorFixo(dto.valorFixo());
        var taxaSalva = repository.save(taxa);

        return TaxaResponseDTO.fromEntity(taxaSalva);

    }


    public void deletarTaxa(String id) {
        var taxa = buscarTaxaPorId(id);
        repository.delete(taxa);
    }
}