package com.senai.Conta_Bancaria.domain.repository;

import com.senai.Conta_Bancaria.domain.entity.CodigoAutenticacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodigoAutenticacaoRepository extends JpaRepository<CodigoAutenticacao, String> {
}
