package com.senai.Conta_Bancaria.domain.repository;

import com.senai.Conta_Bancaria.domain.entity.ContaPoupanca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContaPoupancaRepository extends JpaRepository<ContaPoupanca, String> {
}


